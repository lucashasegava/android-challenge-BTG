package com.example.btg_challenge.features.dashboard.movie

import com.example.btg_challenge.models.MoviesResponseModel

class MoviesViewModel {
    companion object {
        const val MOVIE_MODEL_KEY = "moviemodelkey"
        const val MOVIE_POSITION_KEY = "moviepositionkey"
    }

    fun updateFavoriteMovies(
        movies: MoviesResponseModel,
        position: Int,
        condition: Boolean
    ): MoviesResponseModel {
        if (condition) {
//            movies.favoriteMovies.add(movies.results?.get(position))
            if (movies.favoriteMovies == null) {
                val movielist = ArrayList<MoviesResponseModel.Result>()
                movies.favoriteMovies = movielist
            }
            movies.results?.get(position)?.let { movies.favoriteMovies.add(it) }
        } else {
            movies.favoriteMovies.remove(movies.results?.get(position))
        }
        movies.results!![position].favorite = condition
        return movies
    }


}