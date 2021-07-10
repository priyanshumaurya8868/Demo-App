package com.priyanshumaurya8868.demoapp.ui.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.priyanshumaurya8868.demoapp.api.model.entities.Employee
import com.priyanshumaurya8868.demoapp.databinding.EmployeesItemViewBinding
import com.priyanshumaurya8868.demoapp.load

class EmployeesAdapter(val openDetailsFragListener : (Employee)-> Unit)  : ListAdapter<Employee, EmployeesAdapter.EmployeesVH>(object :
    DiffUtil.ItemCallback<Employee>() {
    override fun areItemsTheSame(oldItem: Employee, newItem: Employee)= oldItem== newItem

    override fun areContentsTheSame(oldItem: Employee, newItem: Employee) = oldItem.toString() == newItem.toString()

}) {


    inner class EmployeesVH(val binding: EmployeesItemViewBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EmployeesVH {
        val inflater = parent.context.getSystemService(LayoutInflater::class.java)
        val  binding = EmployeesItemViewBinding.inflate(inflater,parent,false)
        return EmployeesVH(binding)
    }

    override fun onBindViewHolder(holder: EmployeesVH, position: Int) {
        val item = getItem(position)
        holder.binding.apply {
            pfp.load(item.photopath)
            nameTv.text = item.empname
            designationTv.text = item.designation+"(${item.department})"
            root.setOnClickListener {
               openDetailsFragListener(item)
            }
        }
    }


}