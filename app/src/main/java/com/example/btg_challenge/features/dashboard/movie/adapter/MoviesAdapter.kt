package com.example.btg_challenge.features.dashboard.movie.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.content.ContextCompat
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


    private lateinit var movies: ArrayList<MoviesResponseModel.Result>
    private val picasso = Picasso.get()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MoviesViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.rv_movie_details, parent, false)
        return MoviesViewHolder(view)
    }

    override fun getItemCount(): Int {
        return if (!movies.isNullOrEmpty()) {
            movies.size
        } else {
            0
        }
    }

    override fun onBindViewHolder(holder: MoviesViewHolder, position: Int) {
        val movie = movies[position]
        val context = holder.favoriteImageView.context

        if (!movie.posterPath.isNullOrEmpty()) {
            val uri = EndPointsConstants.BASE_URL_POSTER + movie.posterPath
            ContextCompat.getDrawable(context, R.drawable.ic_local_movies_blue_50dp)?.let {
                picasso.load(uri).placeholder(
                    it
                ).into(holder.imageView)
            }
        }


        if (movie.favorite) {
            holder.favoriteImageView.setImageResource(R.drawable.ic_favorite_red_24dp)
        } else {
            holder.favoriteImageView.setImageResource(R.drawable.ic_favorite_border_red_24dp)
        }

        holder.favoriteImageView.setOnClickListener {
//            movies[position].favorite = !movies[position].favorite
            movie.favorite = !movie.favorite
            if (movie.favorite) {
                holder.favoriteImageView.setImageResource(R.drawable.ic_favorite_red_24dp)
            } else {
                holder.favoriteImageView.setImageResource(R.drawable.ic_favorite_border_red_24dp)
            }
            listener.updateFavoriteMoviesList(movie, movie.favorite)
        }

        holder.touchAreaView.setOnClickListener {
            listener.openMovieDetailsActivity(position)
        }
        holder.viewDataBinding?.setVariable(BR.movieModel, movie)
    }

    fun setMoviesResponseModel(movies: ArrayList<MoviesResponseModel.Result>) {
        this.movies = movies
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