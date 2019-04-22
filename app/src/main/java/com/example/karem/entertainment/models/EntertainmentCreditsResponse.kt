package com.example.karem.entertainment.models

import com.google.gson.annotations.SerializedName

class EntertainmentCreditsResponse {
    @SerializedName("cast") var cast:ArrayList<CastBody>?= null
    @SerializedName("crew") var crew: ArrayList<CrewBody?>?= null
}