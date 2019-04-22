package com.example.karem.entertainment.interfaccs

import com.example.karem.entertainment.models.MovieInfoResponse
import com.example.karem.entertainment.models.TvInfoResponse

interface DetailsFragmentCommunication {
    fun setMovieDetails(movieDetails: MovieInfoResponse){}
    fun setTvDetails(tvDetails: TvInfoResponse){}
}