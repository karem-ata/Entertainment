package com.example.karem.entertainment.interactors

import com.example.karem.entertainment.contracts.EntertainmentInfoDetailsContract
import com.example.karem.entertainment.models.EntertainmentCreditsResponse
import com.example.karem.entertainment.network.ApiClient
import com.example.karem.entertainment.network.EntertainmentService
import com.example.karem.entertainment.utils.Constants
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class InfoFragmentInteractor(val onFinishedListener: EntertainmentInfoDetailsContract.Interactor.OnFinishedListener): EntertainmentInfoDetailsContract.Interactor {

    override fun getMovieCrew(movieId: Int) {
        val movieInfoService = ApiClient().getClient()?.create(EntertainmentService::class.java)
        val movieCreditsCall = movieInfoService?.getMovieCredits(Constants.API_VERSION, movieId, Constants.API_KEY)
        movieCreditsCall?.enqueue(object : Callback<EntertainmentCreditsResponse> {

            override fun onResponse(call: Call<EntertainmentCreditsResponse>, response: Response<EntertainmentCreditsResponse>) {
                if (response.body() != null)
                    onFinishedListener.onMovieCreditCallFinished(response.body()!!)
            }

            override fun onFailure(call: Call<EntertainmentCreditsResponse>, t: Throwable) {
                onFinishedListener.onFailure(t)
            }
        })
    }
}
