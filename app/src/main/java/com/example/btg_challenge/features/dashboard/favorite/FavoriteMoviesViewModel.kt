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

    fun setupSearchBar(titleEt: EditText, yearEt: EditText) {

        var yearActive = false
        var titleActive = false
        var moviesYearFilter = ArrayList<MoviesResponseModel.Result>()
        var moviesTitleFilter = ArrayList<MoviesResponseModel.Result>()

        titleEt.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                if (yearActive) {
                    if (s.toString().isNullOrEmpty()) {
                        titleActive = false
                        favoriteMoviesViewModelInterface.getMovieFromSearchBar(moviesYearFilter)
                    } else {
                        moviesTitleFilter.clear()
                        titleActive = true
                        val results = moviesYearFilter.filter {
                            it.title.toUpperCase().contains(s.toString().toUpperCase())
                        }
                        val moviesWithTitleYearFiltered = ArrayList<MoviesResponseModel.Result>()
                        results.let { moviesWithTitleYearFiltered.addAll(it) }
                        favoriteMoviesViewModelInterface.getMovieFromSearchBar(
                            moviesWithTitleYearFiltered
                        )
                    }
                } else {
                    if (s.toString().isNullOrEmpty()) {
                        titleActive = false
                        if (movies.favoriteMovies != null) {
                            favoriteMoviesViewModelInterface.getMovieFromSearchBar(movies.favoriteMovies)
                        }
                    } else {
                        moviesTitleFilter.clear()
                        titleActive = true
                        val results = movies.favoriteMovies.filter {
                            it.title.toUpperCase().contains(s.toString().toUpperCase())
                        }
                        moviesTitleFilter = ArrayList<MoviesResponseModel.Result>()
                        results.let { moviesTitleFilter.addAll(it) }
                        favoriteMoviesViewModelInterface.getMovieFromSearchBar(
                            moviesTitleFilter
                        )
                    }
                }

            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }

        })


        yearEt.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                if (titleActive) {
                    if (s.toString().isNullOrEmpty()) {
                        yearActive = false
                        favoriteMoviesViewModelInterface.getMovieFromSearchBar(moviesTitleFilter)
                    } else {
                        moviesYearFilter.clear()
                        yearActive = true
                        val result = moviesTitleFilter.filter {
                            val date: Date =
                                SimpleDateFormat("yyyy-MM-dd").parse(it.releaseDate)
                            val calendar = Calendar.getInstance()
                            calendar.time = date
                            calendar.get(Calendar.YEAR).toString().contains(s.toString())
                        }
                        moviesYearFilter.addAll(result)
                        favoriteMoviesViewModelInterface.getMovieFromSearchBar(
                            moviesYearFilter
                        )
                    }
                } else {
                    if (s.toString().isNullOrEmpty()) {
                        yearActive = false
                        if (movies.favoriteMovies != null) {
                            favoriteMoviesViewModelInterface.getMovieFromSearchBar(movies.favoriteMovies)
                        }
                    } else {
                        moviesYearFilter.clear()
                        yearActive = true
                        val result = movies.favoriteMovies.filter {
                            val date: Date =
                                SimpleDateFormat("yyyy-MM-dd").parse(it.releaseDate)
                            val calendar = Calendar.getInstance()
                            calendar.time = date
                            calendar.get(Calendar.YEAR).toString().contains(s.toString())
                        }

                        moviesYearFilter.addAll(result)
                        favoriteMoviesViewModelInterface.getMovieFromSearchBar(
                            moviesYearFilter
                        )
                    }
                }

            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }

        })
    }

    fun clearEditText(titleEt: EditText, yearEt: EditText){
        titleEt.text.clear()
        yearEt.text.clear()
    }
    fun updateListViewModel(movies: MoviesResponseModel) {
        this.movies = movies
    }
}