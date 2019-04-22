package com.example.karem.entertainment.models

import com.google.gson.annotations.SerializedName

class TvBody {
    @SerializedName("id") var entertainmentId: Int? = null
    @SerializedName("poster_path") var entertainmentPoster: String? = null
    @SerializedName("original_name") var entertainmentTitle: String? = null
}