package com.example.btg_challenge.features.dashboard.favorite

import com.example.btg_challenge.models.MoviesResponseModel

interface FavoriteMoviesViewModelInterface {
    fun openMovieDetailsActivity(position: Int)
    fun updateMovieList(movie: MoviesResponseModel.Result, condition: Boolean)
    fun getMovieFromSearchBar(movies: ArrayList<MoviesResponseModel.Result>)
}