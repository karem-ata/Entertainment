package com.example.karem.entertainment.models

import com.google.gson.annotations.SerializedName

class CastBody {
    @SerializedName("character") var character: String?= null
    @SerializedName("name") var name: String?= null
    @SerializedName("profile_path") var profilePath:String?= null
}