package com.example.btg_challenge.models


import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.example.btg_challenge.constants.EndPointsConstants
import com.google.gson.annotations.SerializedName
import com.squareup.picasso.Picasso
import java.io.Serializable
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*
import kotlin.collections.ArrayList

@BindingAdapter("imageURL")
fun loadImage(imageView: ImageView, resource: String) {
    Picasso.get().load(resource).into(imageView)
}

data class MoviesResponseModel(
    @SerializedName("page")
    val page: Int?,
    @SerializedName("results")
    var results: ArrayList<Result>?,
    @SerializedName("total_pages")
    val totalPages: Int?,
    @SerializedName("total_results")
    val totalResults: Int?,
    @SerializedName("favoriteMovies")
    var favoriteMovies :ArrayList<Result>  = ArrayList<Result>()
) : Serializable {
    data class Result(
        @SerializedName("adult")
        val adult: Boolean,
        @SerializedName("backdrop_path")
        val backdropPath: String?,
        @SerializedName("genre_ids")
        val genreIds: List<Int>,
        @SerializedName("id")
        val id: Int,
        @SerializedName("original_language")
        val originalLanguage: String,
        @SerializedName("original_title")
        val originalTitle: String,
        @SerializedName("overview")
        val overview: String,
        @SerializedName("popularity")
        val popularity: Double,
        @SerializedName("poster_path")
        val posterPath: String?,
        @SerializedName("release_date")
        val releaseDate: String,
        @SerializedName("title")
        val title: String,
        @SerializedName("video")
        val video: Boolean,
        @SerializedName("vote_average")
        val voteAverage: Double,
        @SerializedName("vote_count")
        val voteCount: Int,
        @SerializedName("favorite")
        var favorite: Boolean
//        ,
//        @SerializedName("date")
//        val date: Date = SimpleDateFormat("yyyy-MM-dd").parse(releaseDate)
//        val date : Date? = SimpleDateFormat("dd/MM/yyyy", Locale("pt", "BR")).parse(releaseDate)
//        val date = sdf.format(releaseDate)
    ) : Serializable
}