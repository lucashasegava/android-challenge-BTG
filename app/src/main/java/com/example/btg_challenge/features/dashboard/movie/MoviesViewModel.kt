package com.example.btg_challenge.features.dashboard.movie

import android.text.Editable
import android.text.TextWatcher
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import com.example.btg_challenge.models.MoviesResponseModel
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class MoviesViewModel(
    val movieViewModelInterface: MovieViewModelInterface,
    var movies: MoviesResponseModel
) {
    companion object {
        const val MOVIE_MODEL_KEY = "moviemodelkey"
        const val MOVIE_POSITION_KEY = "moviepositionkey"
        const val MOVIE_IS_FROM_FAVORITE_KEY = "isfromfavorite"
    }

    fun updateFavoriteMovies(
        movie: MoviesResponseModel.Result,
        movies: MoviesResponseModel,
        condition: Boolean
    ): MoviesResponseModel {
        this.movies = movies
        movies.results?.forEach {
            if (it == movie) {
                if (condition) {
                    if (movies.favoriteMovies == null) {
                        val movielist = ArrayList<MoviesResponseModel.Result>()
                        movies.favoriteMovies = movielist
                    }
                    movies.favoriteMovies.add(movie)
                } else {
                    movies.favoriteMovies.remove(it)
                }
                it.favorite = condition
            }
        }
        return movies
    }

    fun setupSearchBar(et: EditText) {

        et.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                if (s.toString().isNullOrEmpty()) {
                    movies.results?.let { movieViewModelInterface.getMoviesFromSearch(it) }
                } else {
                    val result = movies.results?.filter {
                        it.title.toUpperCase().contains(s.toString().toUpperCase())
                    }
                    val moviesFromSearch = ArrayList<MoviesResponseModel.Result>()
                    result?.let { moviesFromSearch.addAll(it) }
                    movieViewModelInterface.getMoviesFromSearch(moviesFromSearch)
                }
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }

        })
    }

    fun updateViewModelList(movies: MoviesResponseModel){
        this.movies = movies
    }

    fun clearText(et: EditText){
        et.text.clear()
    }


}