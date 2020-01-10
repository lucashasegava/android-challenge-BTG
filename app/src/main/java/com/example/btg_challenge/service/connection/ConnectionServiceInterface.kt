package com.example.btg_challenge.service.connection

interface ConnectionServiceInterface {
    fun onSuccess(result: String)
    fun onFailure(error: String)
}