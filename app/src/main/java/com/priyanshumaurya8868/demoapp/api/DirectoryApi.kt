package com.priyanshumaurya8868.demoapp.api

import com.priyanshumaurya8868.demoapp.api.model.response.CategoryResponse
import com.priyanshumaurya8868.demoapp.api.model.response.EmployeesResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface DirectoryApi {

    @GET("category")
   suspend fun getCategories() : Response<CategoryResponse>

   @GET("employees")
   suspend  fun searchEmployees(
       @Query("categoryid")categoryid : Int,
       @Query("search") search : String?= null
   ) : Response<EmployeesResponse>


}