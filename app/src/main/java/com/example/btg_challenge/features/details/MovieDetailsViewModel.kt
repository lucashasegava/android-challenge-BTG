package com.example.btg_challenge.features.details

import android.widget.ImageView
import com.example.btg_challenge.R
import com.example.btg_challenge.constants.EndPointsConstants
import com.example.btg_challenge.models.GenreMap
import com.example.btg_challenge.models.MoviesResponseModel

class MovieDetailsViewModel(
    private val movies: MoviesResponseModel,
    private val position: Int,
    private val movieDetailsViewModelInterface: MovieDetailsViewModelInterface
) {
    fun getGenres() {
        val genreList = ArrayList<String>()
        var genre = String()
        val result = movies.results?.get(position)
        result?.genreIds?.forEach {
            if (GenreMap.map.contains(it)) {
                GenreMap.map[it]?.let { it1 -> genreList.add(it1) }
            }
        }
        genreList.forEach {
            genre += "$it, "
        }
        movies.results?.get(position)?.let { movieDetailsViewModelInterface.updateMovieModel(it) }
        movieDetailsViewModelInterface.loadPoster(EndPointsConstants.BASE_URL_POSTER + movies.results?.get(position)?.posterPath)
        movieDetailsViewModelInterface.getGenres(genre)
    }

    fun setFavoriteImage(image: ImageView){
        val currentMovie = movies.results!![position]
        if (currentMovie.favorite) {
            image.setImageResource(R.drawable.ic_favorite_red_24dp)
        } else {
            image.setImageResource(R.drawable.ic_favorite_border_red_24dp)
        }
        image.setOnClickListener {
            currentMovie.favorite = !currentMovie.favorite
            if (currentMovie.favorite) {
                image.setImageResource(R.drawable.ic_favorite_red_24dp)
            } else {
                image.setImageResource(R.drawable.ic_favorite_border_red_24dp)
            }
            movies.results[position].favorite = currentMovie.favorite
        }
    }

    fun  getMovieResponseModel() : MoviesResponseModel{
        return movies
    }

}