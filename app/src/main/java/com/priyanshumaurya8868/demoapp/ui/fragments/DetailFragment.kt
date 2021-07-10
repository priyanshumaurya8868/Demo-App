package com.priyanshumaurya8868.demoapp.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.bumptech.glide.RequestManager
import com.priyanshumaurya8868.demoapp.R
import com.priyanshumaurya8868.demoapp.api.model.entities.Employee
import com.priyanshumaurya8868.demoapp.databinding.DetailFragmentBinding
import com.priyanshumaurya8868.demoapp.load
import com.priyanshumaurya8868.demoapp.ui.MainActivity
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class DetailFragment : Fragment() {
    private  var employee : Employee? = null
    private  var _binding : DetailFragmentBinding? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
        employee =   it.getSerializable("key") as Employee
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        ( requireActivity() as MainActivity).menuItem?.let{it.isVisible   = false
        it.collapseActionView()}
        _binding = DetailFragmentBinding.inflate(inflater,container,false)
        return _binding?.root
    }



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        employee?.let {
            _binding?.apply {
            pfp.load(it.photopath)
                nameTv.text = it.empname
                mobNoTv.text = it.contactno
                intercomRTv.text = it.intercomresidence
                intercomOTv.text = it.intercomoffice
                addressTv.text = it.address
                emailTv.text = it.email
                designationTv.text = it.designation+"( ${it.department })"
            }
        }

    }
}