package com.example.btg_challenge.features.dashboard

import android.os.Bundle
import com.example.btg_challenge.R
import com.example.btg_challenge.features.BaseActivity

class DashboardActivity : BaseActivity(), DashboardViewModelInterface{
    private lateinit var dashboardViewModel: DashboardViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
         dashboardViewModel = DashboardViewModel(this, this)
         dashboardViewModel.getMovies()
    }
}