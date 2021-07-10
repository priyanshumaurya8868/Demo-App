package com.priyanshumaurya8868.demoapp.ui

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.priyanshumaurya8868.demoapp.Resource
import com.priyanshumaurya8868.demoapp.api.DirectoryApi
import com.priyanshumaurya8868.demoapp.api.model.response.CategoryResponse
import com.priyanshumaurya8868.demoapp.api.model.response.EmployeesResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import retrofit2.Response
import javax.inject.Inject


@HiltViewModel
class MainViewModel @Inject constructor(
     val app : Application,
    private val api : DirectoryApi,
) : ViewModel() {


       private var CURR_CATEGORY_ID = 1

       private val _categories  = MutableLiveData<Resource<CategoryResponse?>>()
       val categories : LiveData<Resource<CategoryResponse?>> = _categories

       private val _employeesList =  MutableLiveData<Resource<EmployeesResponse?>>()
               val  employeesList : LiveData<Resource<EmployeesResponse?>> = _employeesList

    init {
              getCategories()
              getEmployee()
    }

     fun getCategories() =  viewModelScope.launch {
         _categories.postValue(Resource.Loading())
         try {
             if (hasInternet()) {
                 Log.d("omega", "has Internet coonnection")
                 val categoryResponse = api.getCategories()
                 handleCategoriesResponse(categoryResponse)
             } else {
                _categories.postValue(Resource.Error(msg = "No internet connection...!"))
             }
         }
     catch (e : Exception){
             _categories.postValue(Resource.Error(msg= e.message?:"Something went wrong"))
         }

     }

    private fun hasInternet(): Boolean {
        val cm =
            app.applicationContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        return cm.isActiveNetworkMetered
    }

    private fun  handleCategoriesResponse(categoryResponse: Response<CategoryResponse>) {
     if (categoryResponse.isSuccessful){
         _categories.postValue(Resource.Success(data = categoryResponse.body()))
     }else {
         _categories.postValue(Resource.Error(msg = "Error : ${categoryResponse.code()}"))
     }
    }

    fun getEmployee(empId: Int=CURR_CATEGORY_ID, keyword: String? =null)= viewModelScope.launch {

        Log.d("omega", "for fetching category id $empId  ")
            _employeesList.postValue(Resource.Loading())
            try {
                if (hasInternet()) {
                    val response = api.searchEmployees(empId, keyword)
                    handleEmployeesListResponse(response)
                } else {
                    _employeesList.postValue(Resource.Error(msg = "No InternetConnection...!"))
                }
            } catch (e: Exception) {
                _employeesList.postValue(Resource.Error(msg = "Something went wrong...!"))
                Log.d("omega", "At MainViewModel -> fun getEmployee  ${e.message}")
            }
        CURR_CATEGORY_ID = empId
        }


    private fun  handleEmployeesListResponse(response: Response<EmployeesResponse>) {
        if (response.isSuccessful ){
            _employeesList.postValue(Resource.Success(data = response.body()))
            Log.d("omega" , "fetched category id  ${response.body()?.categoryid?: -1} & ${response.body()?.category}")

            response.body()?.employees?.map { emp->
                Log.d("omega", emp.empname)
            }

        }else {
            _employeesList.postValue(Resource.Error(msg = "Error : ${response.code()} found...!"))
        }
    }



}