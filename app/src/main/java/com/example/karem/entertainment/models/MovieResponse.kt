package com.example.karem.entertainment.models

import com.google.gson.annotations.SerializedName

class MovieResponse {
    @SerializedName("results") var movieResponseResults: ArrayList<MovieBody> ?= null
}