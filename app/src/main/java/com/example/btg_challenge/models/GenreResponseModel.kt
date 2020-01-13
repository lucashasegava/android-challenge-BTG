package com.example.btg_challenge.models


import com.google.gson.annotations.SerializedName

data class GenreResponseModel(
    @SerializedName("genres")
    val genres: List<Genre>?
) {
    data class Genre(
        @SerializedName("id")
        val id: Int,
        @SerializedName("name")
        val name: String
    )
}