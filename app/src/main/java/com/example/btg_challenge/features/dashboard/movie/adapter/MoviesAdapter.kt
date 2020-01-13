package com.example.btg_challenge.features.dashboard.movie.adapter

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
import com.example.btg_challenge.features.dashboard.movie.MovieViewModelInterface
import com.example.btg_challenge.models.MoviesResponseModel
import com.squareup.picasso.Picasso

class MoviesAdapter(private val listener: MovieViewModelInterface) :
    RecyclerView.Adapter<MoviesAdapter.MoviesViewHolder>() {


    private lateinit var movies: MoviesResponseModel
    private lateinit var currentMovie: MoviesResponseModel.Result
    private val picasso = Picasso.get()
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

        if (!currentMovie.posterPath.isNullOrEmpty()) {
            val uri = EndPointsConstants.BASE_URL_POSTER + currentMovie.posterPath
            picasso.load(uri).into(holder.imageView)
        }

        if (currentMovie.favorite) {
            holder.favoriteImageView.setImageResource(R.drawable.ic_favorite_red_24dp)
        } else {
            holder.favoriteImageView.setImageResource(R.drawable.ic_favorite_border_red_24dp)
        }

        holder.favoriteImageView.setOnClickListener {
            movies.results!![position].favorite = !movies.results!![position].favorite
//            currentMovie.favorite = !currentMovie.favorite
            if (movies.results!![position].favorite) {
                holder.favoriteImageView.setImageResource(R.drawable.ic_favorite_red_24dp)
            } else {
                holder.favoriteImageView.setImageResource(R.drawable.ic_favorite_border_red_24dp)
            }
            listener.updateFavoriteMoviesList(movies, position, movies.results!![position].favorite)
        }

        holder.touchAreaView.setOnClickListener {
            listener.openMovieDetailsActivity(position)
        }

        holder.viewDataBinding?.setVariable(BR.movieModel, currentMovie)
    }

    fun setMoviesResponseModel(moviesResponseModel: MoviesResponseModel) {
        this.movies = moviesResponseModel
        notifyDataSetChanged()
    }


    class MoviesViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val viewDataBinding: ViewDataBinding? = DataBindingUtil.bind<ViewDataBinding>(itemView)
        val imageView: ImageView =
            itemView.findViewById<ImageView>(R.id.rvMovieDetailsPosterImageView)
        val favoriteImageView: ImageView =
            itemView.findViewById<ImageView>(R.id.rvMovieDetailsFavoriteImageView)
        val touchAreaView: View = itemView.findViewById<View>(R.id.rvMovieDetailsTouchAreaView)


    }
}