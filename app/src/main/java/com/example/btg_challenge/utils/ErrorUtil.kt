package com.example.btg_challenge.utils

import android.content.Context
import com.example.btg_challenge.R
import com.example.btg_challenge.models.ErrorResponseModel

object ErrorUtil {

    fun getErrorMessage(errorResponseModel: ErrorResponseModel, context: Context): String {
        var message = ""
        when (errorResponseModel.statusCode) {
            7 -> {
                message = context.getString(R.string.error_wrong_api_key)
            }
            34 -> {
                message = context.getString(R.string.error_no_results)
            }
        }
        return message
    }
}