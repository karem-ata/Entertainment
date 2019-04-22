package com.example.karem.entertainment.interactors

import android.util.Log
import com.example.karem.entertainment.contracts.EntertainmentBaseContract
import com.example.karem.entertainment.contracts.TvContract
import com.example.karem.entertainment.models.EntertainmentCategory
import com.example.karem.entertainment.models.TvResponse
import com.example.karem.entertainment.network.ApiClient
import com.example.karem.entertainment.network.EntertainmentService
import com.example.karem.entertainment.utils.Constants
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class TvInteractor(private var onFinishedListener: EntertainmentBaseContract.Interactor.OnFinishedListener):
        TvContract.Interactor {

    override fun getTvCategoryList(currentCategory: EntertainmentCategory) {
        when(currentCategory.name){
            Constants.ENTERTAINMENT_SIMILAR_TV -> getSimilarTv(currentCategory)
            Constants.ENTERTAINMENT_TOP_RATED_TV -> getTopRatedTv(currentCategory)
            Constants.ENTERTAINMENT_TRENDING_TV -> getTrendingTv(currentCategory)
            Constants.ENTERTAINMENT_ON_AIR_TV -> getOnAirTv(currentCategory)
            Constants.ENTERTAINMENT_POPULAR_TV -> getPopularTv(currentCategory)
            else -> getSearchResult(currentCategory)
        }
    }

    private fun getSimilarTv(currentCategory: EntertainmentCategory){
        val similarTvService = ApiClient().getClient()?.create(EntertainmentService::class.java)
        val similarTvCall = similarTvService?.getSimilarTv(Constants.API_VERSION, currentCategory.id!!, Constants.API_KEY, currentCategory.pageNo)

        similarTvCall?.enqueue(object: Callback<TvResponse>{

            override fun onResponse(call: Call<TvResponse>, response: Response<TvResponse>) {
                val similarTvList = response.body()?.tvResponseResults
                onFinishedListener.onEntertainmentCallFinished(similarTvList, currentCategory)
            }

            override fun onFailure(call: Call<TvResponse>, t: Throwable) {
                onFinishedListener.onFailure(t)
            }
        })
    }

    private fun getOnAirTv(entertainmentCategory: EntertainmentCategory) {
        val onAirTvService = ApiClient().getClient()?.create(EntertainmentService::class.java)
        val onAirTvCall = onAirTvService?.getOnAirTv(Constants.API_VERSION, Constants.API_KEY, entertainmentCategory.pageNo)

        onAirTvCall?.enqueue(object : Callback<TvResponse> {
            override fun onResponse(call: Call<TvResponse>, response: Response<TvResponse>) {
                val onAirTvList = response.body()?.tvResponseResults
                onFinishedListener.onEntertainmentCallFinished(onAirTvList, entertainmentCategory)
            }

            override fun onFailure(call: Call<TvResponse>, t: Throwable) {
                onFinishedListener.onFailure(t)
            }
        })
    }

    private fun getTrendingTv(entertainmentCategory: EntertainmentCategory) {
        val trendingTvService = ApiClient().getClient()?.create(EntertainmentService::class.java)
        val trendingTvCall = trendingTvService?.getTrendingTv(Constants.API_VERSION, Constants.API_KEY, entertainmentCategory.pageNo)

        trendingTvCall?.enqueue(object : Callback<TvResponse> {
            override fun onResponse(call: Call<TvResponse>, response: Response<TvResponse>) {
                val trendingTvList = response.body()?.tvResponseResults
                Log.e(";;",call.request().url().toString())
                onFinishedListener.onEntertainmentCallFinished(trendingTvList, entertainmentCategory)
            }

            override fun onFailure(call: Call<TvResponse>, t: Throwable) {
                onFinishedListener.onFailure(t)
            }
        })
    }

    private fun getPopularTv(entertainmentCategory: EntertainmentCategory) {
        val popularTvService = ApiClient().getClient()?.create(EntertainmentService::class.java)
        val popularTvCall = popularTvService?.getPopularTv(Constants.API_VERSION, Constants.API_KEY, entertainmentCategory.pageNo)

        popularTvCall?.enqueue(object : Callback<TvResponse> {
             override fun onResponse(call: Call<TvResponse>, response: Response<TvResponse>) {
                val popularTvList = response.body()?.tvResponseResults
                onFinishedListener.onEntertainmentCallFinished(popularTvList, entertainmentCategory)
            }
            override fun onFailure(call: Call<TvResponse>, t: Throwable) {
                onFinishedListener.onFailure(t)
            }
        })
    }

    private fun getTopRatedTv(entertainmentCategory: EntertainmentCategory) {
        val topRatedTvService = ApiClient().getClient()?.create(EntertainmentService::class.java)
        val topRatedTvCall = topRatedTvService?.getTopRatedTv(Constants.API_VERSION, Constants.API_KEY, entertainmentCategory.pageNo)

        topRatedTvCall?.enqueue(object : Callback<TvResponse> {
            override fun onResponse(call: Call<TvResponse>, response: Response<TvResponse>) {
                val topRatedList = response.body()?.tvResponseResults
                onFinishedListener.onEntertainmentCallFinished(topRatedList, entertainmentCategory)
            }

            override fun onFailure(call: Call<TvResponse>, t: Throwable) {
                onFinishedListener.onFailure(t)
            }
        })
    }

    private fun getSearchResult(entertainmentCategory: EntertainmentCategory){
        val searchTvService = ApiClient().getClient()?.create(EntertainmentService::class.java)
        val searchTvCall = searchTvService?.getSearchTv(Constants.API_VERSION, Constants.API_KEY, entertainmentCategory.name, entertainmentCategory.pageNo)

        searchTvCall?.enqueue(object : Callback<TvResponse> {
            override fun onResponse(call: Call<TvResponse>, response: Response<TvResponse>) {
                val searchResultsList = response.body()?.tvResponseResults
                onFinishedListener.onEntertainmentCallFinished(searchResultsList, entertainmentCategory)
            }

            override fun onFailure(call: Call<TvResponse>, t: Throwable) {
                onFinishedListener.onFailure(t)
            }
        })
    }
}
