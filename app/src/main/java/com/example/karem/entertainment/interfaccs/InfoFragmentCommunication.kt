package com.example.karem.entertainment.interfaccs

import com.example.karem.entertainment.models.MovieInfoResponse
import com.example.karem.entertainment.models.EntertainmentImagesResponse
import com.example.karem.entertainment.models.EntertainmentTrailersResponse
import com.example.karem.entertainment.models.TvInfoResponse

interface InfoFragmentCommunication {
    fun setMovieInfo(movieInfo: MovieInfoResponse, entertainmentImages: EntertainmentImagesResponse, entertainmentTrailers: EntertainmentTrailersResponse?){}
    fun setTvInfo(tvInfo: TvInfoResponse, entertainmentImages: EntertainmentImagesResponse, entertainmentTrailers: EntertainmentTrailersResponse?){}
}