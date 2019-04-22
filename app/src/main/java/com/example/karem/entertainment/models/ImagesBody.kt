package com.example.karem.entertainment.models

import com.google.gson.annotations.SerializedName
import java.io.Serializable

class ImagesBody: Serializable {
    @SerializedName("file_path")var imagePath:String?= null
}