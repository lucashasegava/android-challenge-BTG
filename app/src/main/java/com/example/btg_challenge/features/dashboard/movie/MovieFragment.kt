package com.example.btg_challenge.features.dashboard.movie

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.btg_challenge.R
import com.example.btg_challenge.constants.RequestCodeConstants
import com.example.btg_challenge.features.dashboard.DashboardActivity
import com.example.btg_challenge.features.dashboard.DashboardViewModel
import com.example.btg_challenge.features.dashboard.movie.adapter.MoviesAdapter
import com.example.btg_challenge.features.details.MovieDetailsActivity
import com.example.btg_challenge.models.MoviesResponseModel
import kotlinx.android.synthetic.main.fragment_movie.*

class MovieFragment : Fragment(), MovieViewModelInterface {


    private lateinit var moviesResponseModel: MoviesResponseModel
    private lateinit var moviesViewModel: MoviesViewModel
    private lateinit var adapter: MoviesAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_movie, container, false)
        getMoviesResponseModel()

        val recyclerView = view?.findViewById(R.id.fragmentMovieListRecyclerView) as RecyclerView
        adapter = MoviesAdapter(this)
        recyclerView.adapter = adapter
        moviesResponseModel.results?.let { adapter.setMoviesResponseModel(it) }
        val layoutManager = LinearLayoutManager(activity)
        recyclerView.layoutManager = layoutManager
        moviesViewModel =
            MoviesViewModel(this, moviesResponseModel)
        moviesViewModel.setupSearchBar(view.findViewById(R.id.fragmentMovieSearchBoxEditText))
        return view
    }

    private fun getMoviesResponseModel() {
        if (arguments != null) {
            if (arguments!!.containsKey(DashboardViewModel.MOVIES_KEY)) {
                moviesResponseModel =
                    arguments!!.getSerializable(DashboardViewModel.MOVIES_KEY) as MoviesResponseModel
            }
        }
    }

    override fun onResume() {
        super.onResume()
        moviesResponseModel.results?.let { adapter.setMoviesResponseModel(it) }
    }

    override fun updateFavoriteMoviesList(
        movies: MoviesResponseModel.Result,
        condition: Boolean
    ) {
        moviesResponseModel = moviesViewModel.updateFavoriteMovies(movies, moviesResponseModel, condition)
//        val dashboardActivity = activity as DashboardActivity
//        dashboardActivity.notifyFavoriteListChanged(moviesResponseModel)
    }

    override fun openMovieDetailsActivity(position: Int) {
        val intent = Intent(activity, MovieDetailsActivity::class.java)
        intent.putExtra(MoviesViewModel.MOVIE_MODEL_KEY, moviesResponseModel)
        intent.putExtra(MoviesViewModel.MOVIE_POSITION_KEY, position)
        activity?.startActivityForResult(intent, RequestCodeConstants.START_MOVIE_DETAILS_ACTIVITY)
    }

    fun updateModelFromRecyclerView(movies: MoviesResponseModel){
        moviesResponseModel = movies
//        movies.results?.let { adapter.setMoviesResponseModel(it) }
    }

    fun setMovieResponseModel(movies: MoviesResponseModel){
        this.moviesResponseModel = movies
    }


    override fun getMoviesFromSearch(movies: ArrayList<MoviesResponseModel.Result>) {
        adapter.setMoviesResponseModel(movies)
    }



}