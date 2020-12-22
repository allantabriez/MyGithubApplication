package com.example.my2ndsubmission.repository

import com.example.my2ndsubmission.BuildConfig
import com.loopj.android.http.AsyncHttpClient
import com.loopj.android.http.AsyncHttpResponseHandler

class LoopJ {
    private val client = AsyncHttpClient()
    private  val apiKey = BuildConfig.API_KEY

    fun getData(url: String, responseHandler: AsyncHttpResponseHandler){
        client.addHeader("Authorization", apiKey)
        client.addHeader("User-Agent", "request")
        client.get(url, responseHandler)
    }
}