package com.example.btg_challenge.features.dashboard.movie.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.btg_challenge.BR
import com.example.btg_challenge.R
import com.example.btg_challenge.constants.EndPointsConstants
import com.example.btg_challenge.models.MoviesResponseModel
import com.squareup.picasso.Picasso

class MoviesAdapter : RecyclerView.Adapter<MoviesViewHolder>() {
    private lateinit var movies: MoviesResponseModel
    private lateinit var currentMovie: MoviesResponseModel.Result
    val picasso = Picasso.get()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MoviesViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.rv_movie_details, parent, false)
        return MoviesViewHolder(view)
    }

    override fun getItemCount(): Int {
        return if (!movies.results.isNullOrEmpty()) {
            movies.results!!.size
        } else {
            0
        }
    }

    override fun onBindViewHolder(holder: MoviesViewHolder, position: Int) {
        currentMovie = movies.results!![position]
        if (!currentMovie.posterPath.isNullOrEmpty()){
            val uri = EndPointsConstants.BASE_URL_POSTER+currentMovie.posterPath
            picasso.load(uri).into(holder.imageView)
        }
        holder.viewDataBinding?.setVariable(BR.movieModel, currentMovie)
    }

    fun setMoviesResponseModel(moviesResponseModel: MoviesResponseModel) {
        this.movies = moviesResponseModel
        notifyDataSetChanged()
    }
}