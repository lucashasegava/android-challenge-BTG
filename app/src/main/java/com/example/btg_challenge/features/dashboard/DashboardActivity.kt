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
    private lateinit var moviesResponseModel: MoviesResponseModel

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
        val pageTitleList = ArrayList<String>()
        pageTitleList.add(getString(R.string.movies))
        pageTitleList.add(getString(R.string.favorites))
        adapter.setMoviesResponse(moviesResponseModel, list, pageTitleList)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            RequestCodeConstants.START_MOVIE_DETAILS_ACTIVITY -> {
                val moviesResponseModel = data?.extras?.getSerializable("") as MoviesResponseModel
                movieFragment.updateModelFromRecyclerView(moviesResponseModel)
                notifyFavoriteListChanged(moviesResponseModel)
            }
            RequestCodeConstants.START_MOVIE_DETAILS_ACTIVITY_FROM_FAVORITES -> {
                val moviesResponseModel = data?.extras?.getSerializable("") as MoviesResponseModel
                favoriteMoviesFragment.updateModelFromRecyclerView(moviesResponseModel)
                notifyDefaultListChanged(moviesResponseModel)
            }
        }
    }

    private fun notifyDefaultListChanged(moviesResponseModel: MoviesResponseModel) {
        movieFragment.setMovieResponseModel(moviesResponseModel)
    }

    private fun notifyFavoriteListChanged(moviesResponseModel: MoviesResponseModel) {
        favoriteMoviesFragment.setMovieResponseModel(moviesResponseModel)
    }
}