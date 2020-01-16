package com.example.btg_challenge.features.dashboard.favorite.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import com.example.btg_challenge.BR
import com.example.btg_challenge.R
import com.example.btg_challenge.constants.EndPointsConstants
import com.example.btg_challenge.features.dashboard.favorite.FavoriteMoviesViewModelInterface
import com.example.btg_challenge.models.MoviesResponseModel
import com.squareup.picasso.Picasso

class FavoriteMoviesAdapter(val listener: FavoriteMoviesViewModelInterface) :
    RecyclerView.Adapter<FavoriteMoviesAdapter.FavoriteMoviesViewHolder>() {
    private var movies = ArrayList<MoviesResponseModel.Result>()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteMoviesViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.rv_movie_details, parent, false)
        return FavoriteMoviesViewHolder(
            view
        )
    }

    override fun getItemCount(): Int {
        return if (!movies.isNullOrEmpty()) {
            movies.size
        } else {
            0
        }
    }

    override fun onBindViewHolder(holder: FavoriteMoviesViewHolder, position: Int) {
        val currentMovie = movies[position]
        val context = holder.favoriteImageView.context
        if (!currentMovie.posterPath.isNullOrEmpty()) {
            val uri = EndPointsConstants.BASE_URL_POSTER + currentMovie.posterPath
            ContextCompat.getDrawable(context, R.drawable.ic_local_movies_blue_50dp)?.let {
                Picasso.get().load(uri).placeholder(
                    it
                ).into(holder.imageview)
            }
        }
        if (currentMovie.favorite) {
            holder.favoriteImageView.setImageResource(R.drawable.ic_favorite_red_24dp)
        } else {
            holder.favoriteImageView.setImageResource(R.drawable.ic_favorite_border_red_24dp)
        }
        holder.favoriteImageView.setOnClickListener {
            currentMovie.favorite = !currentMovie.favorite
            if (currentMovie.favorite) {
                holder.favoriteImageView.setImageResource(R.drawable.ic_favorite_red_24dp)
            } else {
                holder.favoriteImageView.setImageResource(R.drawable.ic_favorite_border_red_24dp)
            }
            listener.updateMovieList(currentMovie, currentMovie.favorite)
        }
        holder.touchAreaView.setOnClickListener {
            listener.openMovieDetailsActivity(position)
        }
        holder.viewDataBinding?.setVariable(BR.movieModel, currentMovie)

    }

    fun setMovieResponseModel(movies: ArrayList<MoviesResponseModel.Result>) {
        this.movies = movies
        notifyDataSetChanged()

    }

    class FavoriteMoviesViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val viewDataBinding = DataBindingUtil.bind<ViewDataBinding>(view)
        val imageview = view.findViewById<ImageView>(R.id.rvMovieDetailsPosterImageView)
        val favoriteImageView = view.findViewById<ImageView>(R.id.rvMovieDetailsFavoriteImageView)
        val touchAreaView: View = itemView.findViewById<View>(R.id.rvMovieDetailsTouchAreaView)
    }
}