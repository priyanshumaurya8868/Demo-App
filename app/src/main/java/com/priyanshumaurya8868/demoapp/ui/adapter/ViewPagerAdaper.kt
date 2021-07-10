package com.priyanshumaurya8868.demoapp.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.priyanshumaurya8868.demoapp.api.model.entities.Category
import com.priyanshumaurya8868.demoapp.api.model.entities.Employee
import com.priyanshumaurya8868.demoapp.databinding.DirectoryViewPagerItemBinding

class ViewPagerAdaper(private val openDetailsFragListener : (Employee)-> Unit) :
    RecyclerView.Adapter<ViewPagerAdaper.ViewPagerViewHolder>() {
    var categoryList: List<Category>? = null
    private var employeesList : List<Employee>? = null
    var _adapter : EmployeesAdapter?= null
    inner class ViewPagerViewHolder(val binding: DirectoryViewPagerItemBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewPagerViewHolder {
        val inflater = parent.context.getSystemService(LayoutInflater::class.java)
        val binding = DirectoryViewPagerItemBinding.inflate(inflater, parent, false)
        return ViewPagerViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewPagerViewHolder, position: Int) {

        holder.binding.apply {

               _adapter = EmployeesAdapter(openDetailsFragListener)
                 _adapter!!.submitList(employeesList)
                rvEmployees.adapter = _adapter

            }
        }

    fun setEmployeesList(newList : List<Employee>?){
       employeesList = newList
       _adapter?.submitList(employeesList)
   }

    override fun getItemCount(): Int {
        return categoryList?.size ?: 1
    }

}