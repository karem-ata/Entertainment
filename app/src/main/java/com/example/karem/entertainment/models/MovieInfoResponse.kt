package com.example.karem.entertainment.models

import com.google.gson.annotations.SerializedName

class MovieInfoResponse {
    @SerializedName("budget") var budget: String?= null
    @SerializedName("revenue") var revenue: String?= null
    @SerializedName("original_language") var originalLanguage: String?= null
    @SerializedName("original_title") var originalTitle: String?= null
    @SerializedName("title") var title:String?= null
    @SerializedName("overview") var overview: String?= null
    @SerializedName("poster_path") var posterPath:String?= null
    @SerializedName("backdrop_path") var backdropPath: String?= null
    @SerializedName("release_date") var releaseDate: String?= null
    @SerializedName("runtime") var runtime: String?= null
    @SerializedName("imdb_id") var imdbId: String?=null
    @SerializedName("tagline") var tagLine: String?= null
    @SerializedName("vote_average") var rating:Float?= null
    @SerializedName("vote_count") var voteCount: Int?=null
    @SerializedName("production_companies") var productionCompanies : ArrayList<ProductionCompaniesBody>?= null
    @SerializedName("genres") var genres: ArrayList<GenreBody>?= null

}