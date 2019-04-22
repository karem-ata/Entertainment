package com.example.karem.entertainment.network

import com.example.karem.entertainment.utils.Constants
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ApiClient {
    private var retrofit: Retrofit?= null
    fun getClient():Retrofit?{
        if(retrofit == null){

            retrofit = Retrofit.Builder().
                baseUrl(Constants.BASE_URL).
                addConverterFactory(GsonConverterFactory.create()).build()
        }
        return retrofit
    }
}