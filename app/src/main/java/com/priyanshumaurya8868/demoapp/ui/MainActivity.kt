package com.priyanshumaurya8868.demoapp.ui

import android.os.Bundle
import android.view.Menu
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.widget.SearchView
import androidx.appcompat.widget.Toolbar
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.priyanshumaurya8868.demoapp.Constant
import com.priyanshumaurya8868.demoapp.R
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Job
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private var navController: NavController?=null
    private val viewModel: MainViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)

        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.fragmentContainerView) as NavHostFragment?
      navController = navHostFragment!!.navController

        val myToolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(myToolbar)
        NavigationUI.setupWithNavController(myToolbar, navController!!)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.my_menu,menu)

        val menuItem = menu?.findItem(R.id.search_menu)

       val searchView = menuItem?.actionView as SearchView

        searchView.queryHint = "Search Here"

        searchView.isSubmitButtonEnabled = true

        searchView.setBackgroundResource(R.drawable.search_view_background)


        searchView.setOnQueryTextListener(object: SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
               viewModel.getEmployee(keyword = query)
                return true
            }
            var job : Job? = null
            override fun onQueryTextChange(newText: String?): Boolean {
                if (newText != null) {
                    if (newText.isNotBlank()){
                        job?.cancel()
                        job = MainScope().launch {
                            delay(Constant.SEARCH_TIME_DELAY)
                            viewModel.getEmployee(keyword = newText)
                        }
                    }
                }
                return true
            }

        })

        return super.onCreateOptionsMenu(menu)

    }
}