package com.example.karem.entertainment.models

import com.google.gson.annotations.SerializedName

class EntertainmentTrailersResponse {
    @SerializedName("results") var trailerResults: ArrayList<TrailerBody>?= null
}