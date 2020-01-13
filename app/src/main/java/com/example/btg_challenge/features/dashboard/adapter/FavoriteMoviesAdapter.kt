package com.example.btg_challenge.features.dashboard.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import com.example.btg_challenge.BR
import com.example.btg_challenge.R
import com.example.btg_challenge.constants.EndPointsConstants
import com.example.btg_challenge.models.MoviesResponseModel
import com.squareup.picasso.Picasso

class FavoriteMoviesAdapter :
    RecyclerView.Adapter<FavoriteMoviesAdapter.FavoriteMoviesViewHolder>() {
    private lateinit var movies : MoviesResponseModel
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteMoviesViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.rv_movie_details, parent, false)
        return FavoriteMoviesViewHolder(view)
    }

    override fun getItemCount(): Int {
        return if (!movies.favoriteMovies.isNullOrEmpty()){
            movies.favoriteMovies.size
        } else{
            0
        }
    }

    override fun onBindViewHolder(holder: FavoriteMoviesViewHolder, position: Int) {
        val currentMovie = movies.favoriteMovies[position]
        Picasso.get().load(EndPointsConstants.BASE_URL_POSTER + currentMovie.posterPath).into(holder.imageview)
        holder.viewDataBinding?.setVariable(BR.movieModel, currentMovie)
    }

    fun setMovieResponseModel(movies : MoviesResponseModel){
        this.movies = movies
        notifyDataSetChanged()

    }
    class FavoriteMoviesViewHolder(view: View) : RecyclerView.ViewHolder(view){
        val viewDataBinding = DataBindingUtil.bind<ViewDataBinding>(view)
        val imageview = view.findViewById<ImageView>(R.id.rvMovieDetailsPosterImageView)

    }
}