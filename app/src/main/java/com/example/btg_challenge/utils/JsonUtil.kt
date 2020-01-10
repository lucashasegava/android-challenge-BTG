package com.example.btg_challenge.utils

import com.google.gson.Gson
import com.google.gson.GsonBuilder

object JsonUtil {

    private var mGson: Gson? = null

    fun convertJsonToString(`object`: Any): String {
        if (mGson == null) {
            mGson = GsonBuilder()
                .serializeNulls()
                .excludeFieldsWithoutExposeAnnotation()
                .create()

        }
        return mGson!!.toJson(`object`)
    }

    fun convertStringtoJson(jsonInString: String, `object`: Any): Any? {
        if (mGson == null) {
            mGson = GsonBuilder().serializeNulls().create()
        }
        try {
            return mGson!!.fromJson(jsonInString, `object`.javaClass)
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return null
    }

}