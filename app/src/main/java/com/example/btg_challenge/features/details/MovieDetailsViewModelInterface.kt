package com.example.btg_challenge.features.details

import com.example.btg_challenge.models.MoviesResponseModel

interface MovieDetailsViewModelInterface {
    fun updateMovieModel(movie : MoviesResponseModel.Result)
    fun loadPoster(url: String)
    fun getGenres(genres: String)
}