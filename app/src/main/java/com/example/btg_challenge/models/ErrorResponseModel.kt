package com.example.btg_challenge.models


import com.google.gson.annotations.SerializedName

data class ErrorResponseModel(
    @SerializedName("status_code")
    val statusCode: Int?,
    @SerializedName("status_message")
    val statusMessage: String?,
    @SerializedName("success")
    val success: Boolean?
)