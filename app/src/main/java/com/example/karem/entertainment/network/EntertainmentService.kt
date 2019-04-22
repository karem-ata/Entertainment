package com.example.karem.entertainment.network

import com.example.karem.entertainment.models.*
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface EntertainmentService {

    @GET("/{id}/movie/now_playing")
    fun getNowPlayingMovies(@Path("id")apiVersion: Int, @Query("api_key") apiKey: String, @Query("page") pageNo: Int): Call<MovieResponse>

    @GET("/{id}/movie/upcoming")
    fun getUpComingMovies(@Path("id")apiVersion: Int, @Query("api_key") apiKey: String, @Query("page") pageNo: Int): Call<MovieResponse>

    @GET("/{id}/movie/top_rated")
    fun getTopRatedMovies(@Path("id")apiVersion: Int, @Query("api_key") apiKey: String, @Query("page") pageNo: Int): Call<MovieResponse>

    @GET("/{id}/movie/popular")
    fun getPopularMovies(@Path("id")apiVersion: Int, @Query("api_key") apiKey: String, @Query("page") pageNo: Int): Call<MovieResponse>

    @GET("/{id}/tv/on_the_air")
    fun getOnAirTv(@Path("id")apiVersion: Int, @Query("api_key") apiKey: String, @Query("page") pageNo: Int): Call<TvResponse>

    @GET("/{id}/trending/tv/day?")
    fun getTrendingTv(@Path("id")apiVersion: Int, @Query("api_key") apiKey: String, @Query("page") pageNo: Int): Call<TvResponse>

    @GET("/{id}/tv/top_rated")
    fun getTopRatedTv(@Path("id")apiVersion: Int, @Query("api_key") apiKey: String, @Query("page") pageNo: Int): Call<TvResponse>

    @GET("/{id}/tv/popular")
    fun getPopularTv(@Path("id")apiVersion: Int, @Query("api_key") apiKey: String, @Query("page") pageNo: Int): Call<TvResponse>

    @GET("/{id}/search/tv")
    fun getSearchTv(@Path("id")apiVersion: Int, @Query("api_key") apiKey: String, @Query("query") query: String, @Query("page") pageNo: Int): Call<TvResponse>

    @GET("/{id}/search/movie")
    fun getSearchMovie(@Path("id")apiVersion: Int, @Query("api_key") apiKey: String, @Query("query") query: String, @Query("page") pageNo: Int): Call<MovieResponse>

    @GET("/{id}/movie/{movie_id}")
    fun getMovieDetails(@Path("id")apiVersion: Int, @Path("movie_id") movieId: Int, @Query("api_key") apiKey: String): Call<MovieInfoResponse>

    @GET("/{id}/movie/{movie_id}/credits")
    fun getMovieCredits(@Path("id")apiVersion: Int, @Path("movie_id") movieId: Int, @Query("api_key") apiKey: String): Call<EntertainmentCreditsResponse>

    @GET("/{id}/movie/{movie_id}/images")
    fun getMoviePictures(@Path("id")apiVersion:Int, @Path("movie_id") movieId: Int, @Query("api_key") apiKey: String): Call<EntertainmentImagesResponse>

    @GET("/{id}/movie/{movie_id}/videos")
    fun getMovieVideos(@Path("id")apiVersion: Int, @Path("movie_id") movieId: Int, @Query("api_key") apiKey: String): Call<EntertainmentTrailersResponse>

    @GET("/{id}/movie/{movie_id}/similar")
    fun getSimilarMovies(@Path("id") apiVersion: Int, @Path("movie_id") movieId: Int, @Query("api_key") apiKey: String, @Query("page") pageNo: Int): Call<MovieResponse>

    @GET("/{id}/tv/{tv_id}")
    fun getTVDetails(@Path("id") apiVersion: Int, @Path("tv_id") tvId: Int, @Query("api_key") apiKey: String) : Call<TvInfoResponse>

    @GET("/{id}/tv/{tv_id}/credits")
    fun getTvCredits(@Path("id")apiVersion: Int, @Path("tv_id") tvId: Int, @Query("api_key") apiKey: String): Call<EntertainmentCreditsResponse>

    @GET("/{id}/tv/{tv_id}/images")
    fun getTvImages(@Path("id")apiVersion:Int, @Path("tv_id") tvId: Int, @Query("api_key") apiKey: String): Call<EntertainmentImagesResponse>

    @GET("/{id}/tv/{tv_id}/videos")
    fun getTvVideos(@Path("id")apiVersion: Int, @Path("tv_id") tvId: Int, @Query("api_key") apiKey: String): Call<EntertainmentTrailersResponse>

    @GET("/{id}/tv/{tv_id}/similar")
    fun getSimilarTv(@Path("id") apiVersion: Int, @Path("tv_id") tvId: Int, @Query("api_key") apiKey: String, @Query("page") pageNo: Int): Call<TvResponse>

}