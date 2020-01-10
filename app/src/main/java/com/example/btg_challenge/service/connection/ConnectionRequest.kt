package com.example.btg_challenge.service.connection

import com.android.volley.Response
import com.android.volley.toolbox.StringRequest

open class ConnectionRequest(
    method: Int,
    url: String,
    listener: Response.Listener<String>,
    errorListener: Response.ErrorListener?
) :
    StringRequest(method, url, listener, errorListener) {
    companion object {
        var HEADER_TOKEN = "Authorization"
    }
}
