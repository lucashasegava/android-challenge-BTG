package com.example.btg_challenge.features.dashboard

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.example.btg_challenge.R
import com.example.btg_challenge.features.BaseActivity
import com.example.btg_challenge.features.dashboard.favorite.FavoriteMoviesFragment
import com.example.btg_challenge.features.dashboard.movie.MovieFragment
import com.example.btg_challenge.models.MoviesResponseModel
import kotlinx.android.synthetic.main.activity_dashboard.*

class DashboardActivity : BaseActivity(), DashboardViewModelInterface {


    private lateinit var dashboardViewModel: DashboardViewModel
    private lateinit var adapter: DashboardViewPagerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)
        setupViewPager()
        dashboardViewModel = DashboardViewModel(this, this)
        dashboardViewModel.getMovies()
    }

    private fun setupViewPager() {
        val tabLayout = dashboardActivityTabLayout
        val viewpager = dashboardActivityViewPager
        adapter = DashboardViewPagerAdapter(supportFragmentManager)
        viewpager.adapter = adapter
        tabLayout.setupWithViewPager(viewpager)
    }

    override fun setMoviesResponseModel(moviesResponseModel: MoviesResponseModel) {
        val list = ArrayList<Fragment>()
        list.add(MovieFragment())
        list.add(FavoriteMoviesFragment())
        adapter.setMoviesResponse(moviesResponseModel, list)
    }

    fun loadFragment(fragment: Fragment){
        val transaction : FragmentTransaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.dashboardActivityFrameLayout, fragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }


}