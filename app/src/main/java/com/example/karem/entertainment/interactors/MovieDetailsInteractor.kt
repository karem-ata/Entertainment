package com.example.karem.entertainment.interactors

import android.util.Log
import com.example.karem.entertainment.contracts.MovieDetailsContract
import com.example.karem.entertainment.models.*
import com.example.karem.entertainment.network.ApiClient
import com.example.karem.entertainment.network.EntertainmentService
import com.example.karem.entertainment.utils.Constants
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MovieDetailsInteractor(val onFinishedListener: MovieDetailsContract.Interactor.OnFinishedListener): MovieDetailsContract.Interactor {

    override fun getMoviesDetails(movieId: Int) {

        val movieDetailsService = ApiClient().getClient()?.create(EntertainmentService::class.java)
        val moviesDetailsCall = movieDetailsService?.getMovieDetails(Constants.API_VERSION, movieId, Constants.API_KEY)

        moviesDetailsCall?.enqueue(object: Callback<MovieInfoResponse>{
            override fun onResponse(call: Call<MovieInfoResponse>, response: Response<MovieInfoResponse>) {
                if(response.body()!= null) {
                    onFinishedListener.onMovieDetailsCallFinished(response.body()!!)
                }
            }

            override fun onFailure(call: Call<MovieInfoResponse>, t: Throwable) {
                onFinishedListener.onFailure(t)
            }
        })
    }

    override fun getTvDetails(tvId: Int) {
        val tvDetailsService = ApiClient().getClient()?.create(EntertainmentService::class.java)
        val tvDetailsCall = tvDetailsService?.getTVDetails(Constants.API_VERSION, tvId, Constants.API_KEY)

        tvDetailsCall?.enqueue(object: Callback<TvInfoResponse>{
            override fun onResponse(call: Call<TvInfoResponse>, response: Response<TvInfoResponse>) {

                onFinishedListener.onTvDetailsCallFinished(response.body()!!)
            }

            override fun onFailure(call: Call<TvInfoResponse>, t: Throwable) {
                onFinishedListener.onFailure(t)
            }
        })
    }

    override fun getTvImages(tvId: Int) {
        val tvImagesService = ApiClient().getClient()?.create(EntertainmentService::class.java)
        val tvImagesCall = tvImagesService?.getTvImages(Constants.API_VERSION, tvId, Constants.API_KEY)

        tvImagesCall?.enqueue(object: Callback<EntertainmentImagesResponse>{
            override fun onResponse(call: Call<EntertainmentImagesResponse>, response: Response<EntertainmentImagesResponse>) {
                onFinishedListener.onMovieImagesCallFinished(response.body()!!)
            }

            override fun onFailure(call: Call<EntertainmentImagesResponse>, t: Throwable) {
                onFinishedListener.onFailure(t)
            }
        })
    }

    override fun getTvVideos(tvId: Int) {
        val tvTrailerService = ApiClient().getClient()?.create(EntertainmentService::class.java)
        val tvTrailerCall = tvTrailerService?.getTvVideos(Constants.API_VERSION, tvId, Constants.API_KEY)

        tvTrailerCall?.enqueue(object: Callback<EntertainmentTrailersResponse>{
            override fun onResponse(call: Call<EntertainmentTrailersResponse>, response: Response<EntertainmentTrailersResponse>) {
                onFinishedListener.onMovieVideoCallFinished(response.body()!!)
            }

            override fun onFailure(call: Call<EntertainmentTrailersResponse>, t: Throwable) {
                onFinishedListener.onFailure(t)
            }
        })
    }

    override fun getMovieImages(movieId: Int) {
        val movieImagesService = ApiClient().getClient()?.create(EntertainmentService::class.java)
        val moviesImagesCall = movieImagesService?.getMoviePictures(Constants.API_VERSION, movieId, Constants.API_KEY)

        moviesImagesCall?.enqueue(object: Callback<EntertainmentImagesResponse>{
            override fun onResponse(call: Call<EntertainmentImagesResponse>, response: Response<EntertainmentImagesResponse>) {
                onFinishedListener.onMovieImagesCallFinished(response.body()!!)
            }

            override fun onFailure(call: Call<EntertainmentImagesResponse>, t: Throwable) {
                onFinishedListener.onFailure(t)
            }
        })
    }

    override fun getMovieVideos(movieId: Int) {
        val movieVideosService = ApiClient().getClient()?.create(EntertainmentService::class.java)
        val movieVideosCall = movieVideosService?.getMovieVideos(Constants.API_VERSION, movieId, Constants.API_KEY)

        movieVideosCall?.enqueue(object: Callback<EntertainmentTrailersResponse>{
            override fun onResponse(call: Call<EntertainmentTrailersResponse>, response: Response<EntertainmentTrailersResponse>) {
                if (response.body() != null && response.body()?.trailerResults?.size!! > 0) {
                    onFinishedListener.onMovieVideoCallFinished(response.body())
                }
            }

            override fun onFailure(call: Call<EntertainmentTrailersResponse>, t: Throwable) {
                onFinishedListener.onFailure(t)
            }
        })
    }
}