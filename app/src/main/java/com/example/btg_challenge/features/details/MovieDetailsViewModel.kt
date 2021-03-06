package com.example.btg_challenge.features.details

import android.widget.ImageView
import com.example.btg_challenge.R
import com.example.btg_challenge.constants.EndPointsConstants
import com.example.btg_challenge.models.GenreMap
import com.example.btg_challenge.models.MoviesResponseModel

class MovieDetailsViewModel(
    private val movies: MoviesResponseModel,
    private val position: Int,
    private val isFromFavorite: Boolean,
    private val movieDetailsViewModelInterface: MovieDetailsViewModelInterface
) {

    companion object{
        val MODEL_FROM_MOVIE_DETAILS_KEY = "moviesfrommoviedetails"
    }

    init {
        val url: String
        if (isFromFavorite) {
            movieDetailsViewModelInterface.updateMovieModel(movies.favoriteMovies[position])
            url = EndPointsConstants.BASE_URL_POSTER + movies.favoriteMovies[position].posterPath
        } else {
            movies.results?.get(position)
                ?.let { movieDetailsViewModelInterface.updateMovieModel(it) }
            url = EndPointsConstants.BASE_URL_POSTER + movies.results!![position].posterPath
        }
        movieDetailsViewModelInterface.loadPoster(url)

    }

    fun getGenres() {
        val genreList = ArrayList<String>()
        var genre = String()
        val result = movies.results?.get(position)
        result?.genreIds?.forEach {
            if (GenreMap.map.contains(it)) {
                GenreMap.map[it]?.let { it1 -> genreList.add(it1) }
            }
        }
        genreList.forEach {
            genre += "$it, "
        }
        movieDetailsViewModelInterface.getGenres(genre)
    }

    fun setFavoriteImage(image: ImageView) {
        val currentMovie : MoviesResponseModel.Result = if (isFromFavorite) {
            movies.favoriteMovies[position]
        } else{
            movies.results!![position]
        }
        if (currentMovie.favorite) {
            image.setImageResource(R.drawable.ic_favorite_red_24dp)
        } else {
            image.setImageResource(R.drawable.ic_favorite_border_red_24dp)
        }
        image.setOnClickListener {
            currentMovie.favorite = !currentMovie.favorite
            if (movies.favoriteMovies == null) {
                val movielist = ArrayList<MoviesResponseModel.Result>()
                movies.favoriteMovies = movielist
            }
            if (currentMovie.favorite) {
                image.setImageResource(R.drawable.ic_favorite_red_24dp)
                movies.favoriteMovies.add(currentMovie)
            } else {
                image.setImageResource(R.drawable.ic_favorite_border_red_24dp)
                movies.favoriteMovies.remove(currentMovie)
            }
            movies.results!![position].favorite = currentMovie.favorite
        }
    }

    fun getMovieResponseModel(): MoviesResponseModel {
        return movies
    }

}