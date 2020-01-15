package com.example.btg_challenge.features.dashboard.movie

import com.example.btg_challenge.models.MoviesResponseModel

interface MovieViewModelInterface {
    fun openMovieDetailsActivity(position: Int)
    fun updateFavoriteMoviesList(movies: MoviesResponseModel.Result, condition: Boolean)
    fun getMoviesFromSearch(movies : ArrayList<MoviesResponseModel.Result>)
}