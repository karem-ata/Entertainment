package com.example.karem.entertainment.interactors

import com.example.karem.entertainment.contracts.MovieCastContract
import com.example.karem.entertainment.models.EntertainmentCreditsResponse
import com.example.karem.entertainment.network.ApiClient
import com.example.karem.entertainment.network.EntertainmentService
import com.example.karem.entertainment.utils.Constants
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MovieCastInteractor(private val onCastCallFinished: MovieCastContract.Presenter.OnCastCallFinished): MovieCastContract.Interactor{

    override fun getMovieCastMembers(movieId: Int?) {
        val castService = ApiClient().getClient()?.create(EntertainmentService::class.java)
        val castCall = castService?.getMovieCredits(Constants.API_VERSION, movieId!!, Constants.API_KEY)

        castCall?.enqueue(object: Callback<EntertainmentCreditsResponse>{
            override fun onResponse(call: Call<EntertainmentCreditsResponse>, response: Response<EntertainmentCreditsResponse>) {
                onCastCallFinished.onSuccess(response.body()?.cast)
            }

            override fun onFailure(call: Call<EntertainmentCreditsResponse>, t: Throwable) {
                onCastCallFinished.onFailure(t)
            }
        })
    }

    override fun getTvCastMembers(tvId: Int?) {
        val castService = ApiClient().getClient()?.create(EntertainmentService::class.java)
        val castCall = castService?.getTvCredits(Constants.API_VERSION, tvId!!, Constants.API_KEY)

        castCall?.enqueue(object: Callback<EntertainmentCreditsResponse>{

            override fun onResponse(call: Call<EntertainmentCreditsResponse>, response: Response<EntertainmentCreditsResponse>) {
                onCastCallFinished.onSuccess(response.body()?.cast)
            }

            override fun onFailure(call: Call<EntertainmentCreditsResponse>, t: Throwable) {
                onCastCallFinished.onFailure(t)
            }
        })
    }
}