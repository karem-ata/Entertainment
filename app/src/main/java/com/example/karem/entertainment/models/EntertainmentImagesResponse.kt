package com.example.karem.entertainment.models

import com.google.gson.annotations.SerializedName

class EntertainmentImagesResponse {
    @SerializedName("posters") var posters: ArrayList<ImagesBody>?= null
    @SerializedName("backdrops") var backdrops: ArrayList<ImagesBody>?= null
}