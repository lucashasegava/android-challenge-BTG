package com.example.btg_challenge.features.dashboard

import android.content.Context
import com.example.btg_challenge.constants.EndPointsConstants
import com.example.btg_challenge.constants.QueryConstants
import com.example.btg_challenge.models.MovieResponseModel
import com.example.btg_challenge.models.MoviesResponseModel
import com.example.btg_challenge.service.connection.ConnectionService
import com.example.btg_challenge.service.connection.ConnectionServiceInterface
import com.example.btg_challenge.utils.JsonUtil
import java.util.HashMap

class DashboardViewModel(private var context: Context, private var dashboardViewModelInterface: DashboardViewModelInterface) : ConnectionServiceInterface{


    fun getMovies(){
        val query = HashMap<String, String>()
        query[QueryConstants.API_KEY] = EndPointsConstants.API_KEY
        ConnectionService.getInstance(context).executeGetRequest(query, EndPointsConstants.POPULAR, this)
    }

    override fun onSuccess(result: String) {
        var moviesResponseModel : MovieResponseModel =  MovieResponseModel(null, null, null, null)
        moviesResponseModel = moviesResponseModel.let { JsonUtil.convertStringtoJson(result, it) as MovieResponseModel }
    }

    override fun onFailure(error: String) {
        val teste = true
    }

}