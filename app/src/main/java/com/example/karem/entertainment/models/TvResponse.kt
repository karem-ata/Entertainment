package com.example.karem.entertainment.models

import com.google.gson.annotations.SerializedName

class TvResponse {
    @SerializedName("results") var tvResponseResults: ArrayList<TvBody> ?= null
}