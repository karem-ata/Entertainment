package com.example.karem.entertainment.models

import com.google.gson.annotations.SerializedName

class MovieBody {
    @SerializedName("id") var entertainmentId: Int? = null
    @SerializedName("poster_path") var entertainmentPoster: String? = null
    @SerializedName("title") var entertainmentTitle: String? = null
    @SerializedName("release_date")var releaseDate:String? = null
}