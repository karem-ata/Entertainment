package com.example.karem.entertainment.models

import com.google.gson.annotations.SerializedName

class CrewBody {
    @SerializedName("job") var job: String?= null
    @SerializedName("name") var name:String?= null
    @SerializedName("profile_path")var profilePath:String?= null
}