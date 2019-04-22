package com.example.karem.entertainment.models

import com.google.gson.annotations.SerializedName

class TrailerBody {
    @SerializedName("key") var trailerKey:String?=null
    @SerializedName("name")var trailerTitle: String?= null
}