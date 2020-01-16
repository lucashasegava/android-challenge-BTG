package com.example.btg_challenge.features.dashboard

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.example.btg_challenge.R
import com.example.btg_challenge.constants.RequestCodeConstants
import com.example.btg_challenge.features.BaseActivity
import com.example.btg_challenge.features.dashboard.favorite.FavoriteMoviesFragment
import com.example.btg_challenge.features.dashboard.movie.MovieFragment
import com.example.btg_challenge.features.details.MovieDetailsViewModel
import com.example.btg_challenge.models.ErrorResponseModel
import com.example.btg_challenge.models.MoviesResponseModel
import kotlinx.android.synthetic.main.activity_dashboard.*

class DashboardActivity : BaseActivity(), DashboardViewModelInterface {


    private lateinit var dashboardViewModel: DashboardViewModel
    private lateinit var adapter: DashboardViewPagerAdapter
    private val movieFragment = MovieFragment()
    private val favoriteMoviesFragment = FavoriteMoviesFragment()
    private lateinit var moviesResponseModel: MoviesResponseModel
    private lateinit var dialog: AlertDialog

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
        val pageTitleList = ArrayList<String>()
        pageTitleList.add(getString(R.string.movies))
        pageTitleList.add(getString(R.string.favorites))
        val list = ArrayList<Fragment>()
        list.add(movieFragment)
        list.add(favoriteMoviesFragment)
        adapter.setMoviesResponse(moviesResponseModel, list, pageTitleList)
    }

    override fun setErrorResponse(error: String) {
        val builder = AlertDialog.Builder(this)
        builder.setTitle(R.string.error)
        builder.setMessage(error)
        val layoutInflater = layoutInflater
        val dialogView = layoutInflater.inflate(R.layout.alert_dialog_default, null)

        val positiveButton = dialogView.findViewById<TextView>(R.id.alertDialogDefaultOk)

        builder.setView(dialogView)
        dialog = builder.create()
        positiveButton.setOnClickListener(View.OnClickListener {
            dialog.dismiss()
        })
        dialog.show()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            RequestCodeConstants.START_MOVIE_DETAILS_ACTIVITY -> {
                val moviesResponseModel =
                    data?.extras?.getSerializable(MovieDetailsViewModel.MODEL_FROM_MOVIE_DETAILS_KEY) as MoviesResponseModel
                movieFragment.updateModelFromRecyclerView(moviesResponseModel)
                notifyFavoriteListChanged(moviesResponseModel)
            }
            RequestCodeConstants.START_MOVIE_DETAILS_ACTIVITY_FROM_FAVORITES -> {
                val moviesResponseModel =
                    data?.extras?.getSerializable(MovieDetailsViewModel.MODEL_FROM_MOVIE_DETAILS_KEY) as MoviesResponseModel
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