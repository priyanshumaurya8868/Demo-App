package com.priyanshumaurya8868.demoapp.ui.fragments.directoryFragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.priyanshumaurya8868.demoapp.R
import com.priyanshumaurya8868.demoapp.Resource
import com.priyanshumaurya8868.demoapp.api.model.entities.Category
import com.priyanshumaurya8868.demoapp.api.model.entities.Employee
import com.priyanshumaurya8868.demoapp.api.model.response.CategoryResponse
import com.priyanshumaurya8868.demoapp.api.model.response.EmployeesResponse
import com.priyanshumaurya8868.demoapp.databinding.DirectoryFragmentBinding
import com.priyanshumaurya8868.demoapp.ui.MainActivity
import com.priyanshumaurya8868.demoapp.ui.MainViewModel
import com.priyanshumaurya8868.demoapp.ui.adapter.ViewPagerAdaper
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

@AndroidEntryPoint
class DirectoryFragment : Fragment() {
    private var _binding: DirectoryFragmentBinding? = null
    private var CUR_TAB_POSITION = 0
    private lateinit var madapter: ViewPagerAdaper
    private var job: Job? = null
    private lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(requireActivity()).get(MainViewModel::class.java)
        madapter = ViewPagerAdaper { openDetailFrag(it) }
    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        ( requireActivity() as MainActivity).menuItem?.let{it.isVisible   = true}
        _binding = DirectoryFragmentBinding.inflate(inflater, container, false)
        return _binding?.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        super.onViewCreated(view, savedInstanceState)
        viewModel.categories.observe({ lifecycle }) {
            handelCategoryResource(it)
        }

        viewModel.employeesList.observe({ lifecycle }) {
            handelEmployeesResource(it)
        }
    }
    private fun setUpViewPager(data: List<Category>) {
        madapter.categoryList = data
        _binding?.apply {
            viewPager2.adapter = madapter
            TabLayoutMediator(tabLayout, viewPager2) { tab, position ->
                tab.text = madapter.categoryList?.get(position)?.category.toString()

            }.attach()

            tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
                override fun onTabSelected(tab: TabLayout.Tab?) {
                    job?.cancel()
                    job = lifecycleScope.launch {
                        madapter.categoryList?.let { list ->
                            tab?.position?.let { position ->
                                val _job = viewModel.getEmployee(empId = list[position].categoryid)
                                _job.join()
                                madapter.notifyItemChanged(position)
                            }
                        }
                        job?.cancel()
                        Log.d("omega", "onTabselect thread is running...")
                    }
                }
                override fun onTabUnselected(tab: TabLayout.Tab?) {}
                override fun onTabReselected(tab: TabLayout.Tab?){}
            })
        }
    }
    private fun handelCategoryResource(res: Resource<CategoryResponse?>) {
        when (res) {
            is Resource.Loading -> {
                _binding?.progressCircular?.isVisible = true
            }
            is Resource.Error -> {
                _binding?.progressCircular?.isVisible = false
                Toast.makeText(requireContext(), res.msg, Toast.LENGTH_SHORT).show()
            }
            is Resource.Success -> {
                setUpViewPager(res.data!!.categories)
                _binding?.progressCircular?.isVisible = false
            }
        }
    }
    private fun handelEmployeesResource(res: Resource<EmployeesResponse?>) {
        when (res) {
            is Resource.Loading -> {
                _binding?.apply {
                    progressCircular.isVisible = true
                    emptyListTv.isVisible = false
                    viewPager2.isVisible = false
                }
            }
            is Resource.Error -> {
                _binding?.apply {
                    progressCircular.isVisible = false
                    if (res.data==null){
                        emptyListTv.isVisible = true
                        viewPager2.isVisible = false
                    }
                }
                Toast.makeText(requireContext(), res.msg, Toast.LENGTH_SHORT).show()
            }
            is Resource.Success -> {
                Log.d("omega", "handle res called got suceess")
                _binding?.apply {
                    viewPager2.isVisible = true
                    progressCircular.isVisible = false
                    madapter.setEmployeesList( res.data?.employees)

                    if (res.data?.employees?.isNullOrEmpty() == true){
                         viewPager2.isVisible = false
                          emptyListTv.isVisible = true
                    }
                }
            }
        }
    }
    private fun openDetailFrag(emp: Employee) {
        val bundle = Bundle()
        bundle.putSerializable("key", emp)
        findNavController().navigate(R.id.action_mainFragment_to_detailFragment, bundle)
    }



    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}