package com.example.karem.entertainment.models

import com.google.gson.annotations.SerializedName

class TvInfoResponse {
    @SerializedName("original_language") var originalLanguage: String?= null
    @SerializedName("original_name") var originalTitle: String?= null
    @SerializedName("title") var title:String?= null
    @SerializedName("overview") var overview: String?= null
    @SerializedName("poster_path") var posterPath:String?= null
    @SerializedName("number_of_seasons") var seasonsNo:String?= null
    @SerializedName("backdrop_path") var backdropPath: String?= null
    @SerializedName("first_air_date") var firstAirDate: String?= null
    @SerializedName("last_air_date") var lasttAirDate: String?= null
    @SerializedName("imdb_id") var imdbId: String?=null
    @SerializedName("vote_average") var rating:Float?= null
    @SerializedName("vote_count") var voteCount: Int?=null
    @SerializedName("production_companies") var productionCompanies : ArrayList<ProductionCompaniesBody>?= null
    @SerializedName("genres") var genres: ArrayList<GenreBody>?= null
    @SerializedName("status") var status: String?= null

}
