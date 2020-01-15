package com.example.btg_challenge.features.dashboard.favorite

import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import com.example.btg_challenge.models.MoviesResponseModel
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class FavoriteMoviesViewModel(
    var movies: MoviesResponseModel,
    val favoriteMoviesViewModelInterface: FavoriteMoviesViewModelInterface
) {

    private var originalMovies = ArrayList<MoviesResponseModel.Result>()


    fun updateMovieModel(
        movie: MoviesResponseModel.Result,
        movies: MoviesResponseModel,
        condition: Boolean
    ): MoviesResponseModel {
        movies.results?.forEach {
            if (it == movie) {
                if (condition) {
                    movies.favoriteMovies.add(movie)
                } else {
                    movies.favoriteMovies.remove(it)
                }
                it.favorite = condition
            }
        }
        return movies
    }

    fun setOriginalMovieList(list: ArrayList<MoviesResponseModel.Result>) {
        if (!list.isNullOrEmpty()) {
            originalMovies =
                list.clone() as ArrayList<MoviesResponseModel.Result>
        }
    }

    fun setupSearchBar(titleEt: EditText, yearEt: EditText) {
//        moviesFromSearch = movies
//        titleEt.addTextChangedListener(object : TextWatcher {
//            override fun afterTextChanged(s: Editable?) {
//                if (s.toString().isNullOrEmpty()) {
//                    moviesFromSearch.results = originalMovies
//                } else {
//                    val result = moviesFromSearch.results?.filter {
//                        it.title.toUpperCase().contains(s.toString().toUpperCase())
//                    }
//                    moviesFromSearch.results = result
//                }
//            }
//
//            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
//            }
//
//            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
//            }
//
//        })


//        originalMovies.addAll(movies.favoriteMovies)

        yearEt.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                if (s.toString().isNullOrEmpty()) {
                    favoriteMoviesViewModelInterface.getMovieFromSearchBar(movies.favoriteMovies)
                } else {
                    val result = movies.favoriteMovies.filter {
                        val date: Date =
                            SimpleDateFormat("yyyy-MM-dd").parse(it.releaseDate)
                        val calendar = Calendar.getInstance()
                        calendar.time = date
                        calendar.get(Calendar.YEAR).toString().contains(s.toString())
                    }
                    val filteredMovieListFromSearch = ArrayList<MoviesResponseModel.Result>()
                    filteredMovieListFromSearch.addAll(result)
                    favoriteMoviesViewModelInterface.getMovieFromSearchBar(
                        filteredMovieListFromSearch
                    )
                }
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }

        })
    }
}