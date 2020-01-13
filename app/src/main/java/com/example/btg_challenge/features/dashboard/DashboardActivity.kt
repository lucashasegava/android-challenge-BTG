package com.example.btg_challenge.features.dashboard

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.example.btg_challenge.R
import com.example.btg_challenge.constants.RequestCodeConstants
import com.example.btg_challenge.features.BaseActivity
import com.example.btg_challenge.features.dashboard.favorite.FavoriteMoviesFragment
import com.example.btg_challenge.features.dashboard.movie.MovieFragment
import com.example.btg_challenge.models.MoviesResponseModel
import kotlinx.android.synthetic.main.activity_dashboard.*

class DashboardActivity : BaseActivity(), DashboardViewModelInterface {


    private lateinit var dashboardViewModel: DashboardViewModel
    private lateinit var adapter: DashboardViewPagerAdapter
    private val list = ArrayList<Fragment>()
    private val movieFragment = MovieFragment()
    private val favoriteMoviesFragment = FavoriteMoviesFragment()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)
        setupViewPager()
        dashboardViewModel = DashboardViewModel(this, this)
        dashboardViewModel.getMovies()
        dashboardViewModel.getGenre()
    }

    private fun setupViewPager() {
        val tabLayout = dashboardActivityTabLayout
        val viewpager = dashboardActivityViewPager
        adapter = DashboardViewPagerAdapter(supportFragmentManager)
        viewpager.adapter = adapter
        tabLayout.setupWithViewPager(viewpager)
    }

    override fun setMoviesResponseModel(moviesResponseModel: MoviesResponseModel) {
        list.add(movieFragment)
        list.add(favoriteMoviesFragment)
        adapter.setMoviesResponse(moviesResponseModel, list)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RequestCodeConstants.START_MOVIE_DETAILS_ACTIVITY){
            val moviesResponseModel = data?.extras?.getSerializable("") as MoviesResponseModel
            movieFragment.updateModelFromRecyclerView(moviesResponseModel)
//            adapter.setMoviesResponse(moviesResponseModel, list)
        }
    }

    fun notifyListChanged(moviesResponseModel: MoviesResponseModel){
        favoriteMoviesFragment.setMovieResponseModel(moviesResponseModel)
//        val intent = Intent()
//        intent.setAction("com.example.broadcast.MY_NOTIFICATION")
//        intent.putExtra("", moviesResponseModel)
//        sendBroadcast(intent)
    }
}