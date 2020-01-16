package com.example.btg_challenge.features.dashboard.favorite

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.btg_challenge.R
import com.example.btg_challenge.constants.RequestCodeConstants
import com.example.btg_challenge.features.dashboard.DashboardActivity
import com.example.btg_challenge.features.dashboard.DashboardViewModel
import com.example.btg_challenge.features.dashboard.favorite.adapter.FavoriteMoviesAdapter
import com.example.btg_challenge.features.dashboard.movie.MoviesViewModel
import com.example.btg_challenge.features.details.MovieDetailsActivity
import com.example.btg_challenge.models.MoviesResponseModel
import kotlinx.android.synthetic.main.fragment_favorite_movies.*

class FavoriteMoviesFragment : Fragment(), FavoriteMoviesViewModelInterface{

    private lateinit var moviesResponseModel: MoviesResponseModel
    private lateinit var adapter: FavoriteMoviesAdapter
    private lateinit var favoriteMoviesViewModel: FavoriteMoviesViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_favorite_movies, container, false)

         getMoviesResponseModel()
        val recyclerView = view.findViewById<RecyclerView>(R.id.favoriteMoviesFragmentRecyclerView)
        adapter =
            FavoriteMoviesAdapter(this)
        recyclerView.adapter = adapter
        val layoutManager = LinearLayoutManager(activity)
        recyclerView.layoutManager = layoutManager

        favoriteMoviesViewModel = FavoriteMoviesViewModel(moviesResponseModel, this)
        favoriteMoviesViewModel.setupSearchBar(
            view.findViewById(R.id.favoriteMoviesFragmentTitleSearchBoxEditText),
            view.findViewById(R.id.favoriteMoviesFragmentYearSearchBoxEditText)
        )
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
        if (!moviesResponseModel.favoriteMovies.isNullOrEmpty()) {
            adapter.setMovieResponseModel(moviesResponseModel.favoriteMovies)
            favoriteMoviesViewModel.updateListViewModel(moviesResponseModel)
        }
    }


    override fun openMovieDetailsActivity(
        position: Int
    ) {
        val intent = Intent(activity, MovieDetailsActivity::class.java)
        intent.putExtra(MoviesViewModel.MOVIE_MODEL_KEY, moviesResponseModel)
        intent.putExtra(MoviesViewModel.MOVIE_POSITION_KEY, position)
        intent.putExtra(MoviesViewModel.MOVIE_IS_FROM_FAVORITE_KEY, true)
        activity?.startActivityForResult(
            intent,
            RequestCodeConstants.START_MOVIE_DETAILS_ACTIVITY_FROM_FAVORITES
        )
    }

    fun setMovieResponseModel(moviesResponseModel: MoviesResponseModel) {
        this.moviesResponseModel = moviesResponseModel
        favoriteMoviesViewModel.updateListViewModel(moviesResponseModel)
    }

    override fun onPause() {
        favoriteMoviesViewModel.clearEditText(favoriteMoviesFragmentTitleSearchBoxEditText, favoriteMoviesFragmentYearSearchBoxEditText)
        super.onPause()
    }

//    fun updateModelFromRecyclerView(movies: MoviesResponseModel) {
//        moviesResponseModel = movies
//    }

    override fun getMovieFromSearchBar(movies: ArrayList<MoviesResponseModel.Result>) {
        adapter.setMovieResponseModel(movies)
    }


    override fun updateMovieList(movie: MoviesResponseModel.Result, condition: Boolean) {
        moviesResponseModel =
            favoriteMoviesViewModel.updateMovieModel(movie, moviesResponseModel, condition)
    }

}