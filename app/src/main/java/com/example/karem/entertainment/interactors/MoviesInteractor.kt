package com.example.karem.entertainment.interactors

import com.example.karem.entertainment.contracts.EntertainmentBaseContract
import com.example.karem.entertainment.contracts.MoviesContract
import com.example.karem.entertainment.models.MovieBody
import com.example.karem.entertainment.models.EntertainmentCategory
import com.example.karem.entertainment.models.MovieResponse
import com.example.karem.entertainment.network.ApiClient
import com.example.karem.entertainment.network.EntertainmentService
import com.example.karem.entertainment.utils.Constants
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MoviesInteractor(private var onFinishedListener: EntertainmentBaseContract.Interactor.OnFinishedListener):
        MoviesContract.Interactor {

    override fun getCategoryMoviesList(currentCategory: EntertainmentCategory){
        when(currentCategory.name){
            Constants.ENTERTAINMENT_TOP_RATED_MOVIES -> getTopRatedMovies(currentCategory)
            Constants.ENTERTAINMENT_UPCOMING_MOVIES -> getUpcomingMovies(currentCategory)
            Constants.ENTERTAINMENT_NOW_PLAYING_MOVIES -> getNowPlayingMovies(currentCategory)
            Constants.ENTERTAINMENT_POPULAR_MOVIES -> getPopularMovies(currentCategory)
            Constants.ENTERTAINMENT_SIMILAR_MOVIES -> getSimilarMovies(currentCategory)
            else -> getSearchMovies(currentCategory)
        }
    }

    private fun getNowPlayingMovies(entertainmentCategory: EntertainmentCategory) {
        val pageNo = entertainmentCategory.pageNo
        val nowPlayingMoviesService = ApiClient().getClient()?.create(EntertainmentService::class.java)
        val nowPlayingMovieCall = nowPlayingMoviesService?.getNowPlayingMovies(Constants.API_VERSION, Constants.API_KEY, pageNo)

        nowPlayingMovieCall?.enqueue(object : Callback<MovieResponse> {
            override fun onResponse(call: Call<MovieResponse>, response: Response<MovieResponse>) {
                val nowPlayingMoviesList = response.body()?.movieResponseResults
                onFinishedListener.onEntertainmentCallFinished(nowPlayingMoviesList, entertainmentCategory)
            }

            override fun onFailure(call: Call<MovieResponse>, t: Throwable) {
                onFinishedListener.onFailure(t)
            }
        })
    }

    private fun getUpcomingMovies(entertainmentCategory: EntertainmentCategory) {
        val upcomingMoviesService = ApiClient().getClient()?.create(EntertainmentService::class.java)

        val upcomingMovieCall = upcomingMoviesService?.getUpComingMovies(Constants.API_VERSION, Constants.API_KEY, entertainmentCategory.pageNo)
        upcomingMovieCall?.enqueue(object : Callback<MovieResponse> {
            override fun onResponse(call: Call<MovieResponse>, response: Response<MovieResponse>) {
                val upcomingMoviesList = response.body()?.movieResponseResults
                onFinishedListener.onEntertainmentCallFinished(upcomingMoviesList, entertainmentCategory)
            }

            override fun onFailure(call: Call<MovieResponse>, t: Throwable) {
                onFinishedListener.onFailure(t)
            }
        })
    }

    private fun getTopRatedMovies(entertainmentCategory: EntertainmentCategory) {
        val topRatedMoviesService = ApiClient().getClient()?.create(EntertainmentService::class.java)
        val topRatedMovieCall = topRatedMoviesService?.getTopRatedMovies(Constants.API_VERSION, Constants.API_KEY, entertainmentCategory.pageNo)

        topRatedMovieCall?.enqueue(object : Callback<MovieResponse> {
            override fun onResponse(call: Call<MovieResponse>, response: Response<MovieResponse>) {
                val topRatedMoviesList:ArrayList<MovieBody>? =  response.body()?.movieResponseResults
                onFinishedListener.onEntertainmentCallFinished(topRatedMoviesList, entertainmentCategory)
            }

            override fun onFailure(call: Call<MovieResponse>, t: Throwable) {
                onFinishedListener.onFailure(t)
            }
        })
    }

    private fun getPopularMovies(entertainmentCategory: EntertainmentCategory) {
        val popularMoviesService = ApiClient().getClient()?.create(EntertainmentService::class.java)
        val popularMovieCall = popularMoviesService?.getPopularMovies(Constants.API_VERSION, Constants.API_KEY, entertainmentCategory.pageNo)

        popularMovieCall?.enqueue(object : Callback<MovieResponse> {
            override fun onResponse(call: Call<MovieResponse>, response: Response<MovieResponse>) {
                val popularMoviesList = response.body()?.movieResponseResults
                onFinishedListener.onEntertainmentCallFinished(popularMoviesList, entertainmentCategory)
            }

            override fun onFailure(call: Call<MovieResponse>, t: Throwable) {
                onFinishedListener.onFailure(t)
            }
        })
    }

    private fun getSearchMovies(entertainmentCategory: EntertainmentCategory) {
        val searchMoviesService = ApiClient().getClient()?.create(EntertainmentService::class.java)
        val searchMovieCall = searchMoviesService?.getSearchMovie(Constants.API_VERSION, Constants.API_KEY, entertainmentCategory.name, entertainmentCategory.pageNo)

        searchMovieCall?.enqueue(object : Callback<MovieResponse> {
            override fun onResponse(call: Call<MovieResponse>, response: Response<MovieResponse>) {
                val searchMoviesList = response.body()?.movieResponseResults
                onFinishedListener.onEntertainmentCallFinished(searchMoviesList, entertainmentCategory)
            }

            override fun onFailure(call: Call<MovieResponse>, t: Throwable) {
                onFinishedListener.onFailure(t)
            }
        })
    }

    private fun getSimilarMovies(entertainmentCategory: EntertainmentCategory){
        val similarMoviesService = ApiClient().getClient()?.create(EntertainmentService::class.java)
        val similarMoviesCall = similarMoviesService?.getSimilarMovies(Constants.API_VERSION, entertainmentCategory.id!!, Constants.API_KEY, entertainmentCategory.pageNo)

        similarMoviesCall?.enqueue(object: Callback<MovieResponse>{
            override fun onResponse(call: Call<MovieResponse>, response: Response<MovieResponse>) {
                val similarMovies = response.body()?.movieResponseResults
                onFinishedListener.onEntertainmentCallFinished(similarMovies, entertainmentCategory)
            }

            override fun onFailure(call: Call<MovieResponse>, t: Throwable) {
                onFinishedListener.onFailure(t)
            }
        })
    }
}