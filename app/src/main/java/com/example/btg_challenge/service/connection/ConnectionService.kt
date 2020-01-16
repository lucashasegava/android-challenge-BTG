package com.example.btg_challenge.service.connection

import android.content.Context
import android.util.Log
import com.android.volley.DefaultRetryPolicy
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.Volley
import org.json.JSONException
import org.json.JSONObject
import java.io.UnsupportedEncodingException
import java.nio.charset.Charset

class ConnectionService private constructor(applicationContext: Context) {
    private var mRequestQueue: RequestQueue? = null
    private val mDefaultRetryPolicy: DefaultRetryPolicy
    private val TAG = "CONNECTION_SERVICE"
    private var mBodyParams: String? = null

    companion object {

        private var mInstance: ConnectionService? = null
        private var mApplicationContext: Context? = null

        fun getInstance(applicationContext: Context): ConnectionService {
            if (mInstance == null) {
                mInstance = ConnectionService(applicationContext)
            } else {
                mApplicationContext = applicationContext
            }

            return this.mInstance!!
        }
    }

    init {
        mApplicationContext = applicationContext
        mDefaultRetryPolicy = DefaultRetryPolicy(3000, 2, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT)
    }

    private fun executeRequest(
        method: Int,
        endpoint: String,
        connectionServiceInterface: ConnectionServiceInterface
    ) {
        if (mRequestQueue == null) {
            mRequestQueue = Volley.newRequestQueue(mApplicationContext!!)
        }

        val connectionRequest = object  : ConnectionRequest(method, endpoint,
            Response.Listener { response ->
                var jsonResponse: JSONObject? = null
                try {
                    jsonResponse = JSONObject(response)
                    Log.v(TAG, "log Response: " + jsonResponse.toString(4))
                } catch (e: JSONException) {
                    e.printStackTrace()
                }
                connectionServiceInterface.onSuccess(response)
            }, Response.ErrorListener { error ->
                if (error != null) {
                    Log.v(TAG, error.toString())
                    if (error.networkResponse != null && error.networkResponse.data != null) {
                        var jsonResponse: JSONObject? = null
                        try {
                            jsonResponse = JSONObject(String(error.networkResponse.data, Charset.forName("UTF-8")))
                            Log.v(TAG, "ERROR: " + jsonResponse!!.toString(4))
                        } catch (e: JSONException) {
                            e.printStackTrace()
                        } catch (e: UnsupportedEncodingException) {
                            e.printStackTrace()
                        }
                        connectionServiceInterface.onFailure(jsonResponse.toString())
                    }
                }
            }){
            override fun getPriority(): Priority {
                return Priority.NORMAL
            }
        }
        connectionRequest.retryPolicy = mDefaultRetryPolicy
        mRequestQueue!!.add(connectionRequest)
    }

    private fun generateMediaQuery(params: Map<String, String>?): String {
        var result = ""

        if (params != null && params.size > 0) {
            for ((key, value) in params) {
                result += "$key=$value&"
            }
            result = "?" + result.substring(0, result.length - 1)
        }
        return result
    }

    fun executeGetRequest(
        queryParams: Map<String, String>,
        endPoint: String,
        connectionServiceInterface: ConnectionServiceInterface
    ) {
        val fullUri = endPoint + generateMediaQuery(queryParams)
        executeRequest(Request.Method.GET, fullUri, connectionServiceInterface)
    }

}