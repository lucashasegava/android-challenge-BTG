package com.example.btg_challenge.features.details

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import com.example.btg_challenge.R
import com.example.btg_challenge.constants.EndPointsConstants
import com.example.btg_challenge.constants.RequestCodeConstants
import com.example.btg_challenge.databinding.ActivityMovieDetailsBinding
import com.example.btg_challenge.features.BaseActivity
import com.example.btg_challenge.features.dashboard.movie.MoviesViewModel
import com.example.btg_challenge.models.MoviesResponseModel
import kotlinx.android.synthetic.main.activity_movie_details.*

class MovieDetailsActivity : BaseActivity(), MovieDetailsViewModelInterface {

    private lateinit var activityMovieDetailsBinding: ActivityMovieDetailsBinding
    private lateinit var movieDetailsViewModel: MovieDetailsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityMovieDetailsBinding =
            DataBindingUtil.setContentView(this, R.layout.activity_movie_details)
        val movies =
            intent.getSerializableExtra(MoviesViewModel.MOVIE_MODEL_KEY) as MoviesResponseModel
        val position = intent.getIntExtra(MoviesViewModel.MOVIE_POSITION_KEY, 0)
        movieDetailsViewModel = MovieDetailsViewModel(movies, position, this)
        movieDetailsViewModel.getGenres()
        movieDetailsViewModel.setFavoriteImage(movieDetailsActivityFavoriteImageView)
        activityMovieDetailsBinding.movieModel = movies.results?.get(position)
    }


    override fun updateMovieModel(movie: MoviesResponseModel.Result) {
        activityMovieDetailsBinding.movieModel = movie
    }


    override fun loadPoster(url: String) {
        activityMovieDetailsBinding.imageUrl = url
    }

    override fun getGenres(genres: String) {
        activityMovieDetailsBinding.genreStrings = genres
    }

    fun closeMovieDetailsActivity(view: View){
        val intent = Intent()
        intent.putExtra("", movieDetailsViewModel.getMovieResponseModel())
        setResult(RequestCodeConstants.START_MOVIE_DETAILS_ACTIVITY, intent)
        finish()
    }
}