package com.example.btg_challenge.features.dashboard

import com.example.btg_challenge.models.ErrorResponseModel
import com.example.btg_challenge.models.MoviesResponseModel

interface DashboardViewModelInterface {
    fun setMoviesResponseModel(moviesResponseModel: MoviesResponseModel)
    fun setErrorResponse(error: String)
}