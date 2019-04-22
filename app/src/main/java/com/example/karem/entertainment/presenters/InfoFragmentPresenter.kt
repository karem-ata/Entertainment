package com.example.karem.entertainment.presenters

import android.net.Uri
import com.example.karem.entertainment.contracts.EntertainmentInfoDetailsContract
import com.example.karem.entertainment.interactors.InfoFragmentInteractor
import com.example.karem.entertainment.models.*
import com.example.karem.entertainment.utils.Constants

class InfoFragmentPresenter: EntertainmentInfoDetailsContract.Presenter, EntertainmentInfoDetailsContract.Interactor.OnFinishedListener {

    private val movieInfoInteractor= InfoFragmentInteractor(this)
    private var view: EntertainmentInfoDetailsContract.View?= null

    private lateinit var movieTrailers: ArrayList<TrailerBody>
    private lateinit var entertainmentImagesResponse: EntertainmentImagesResponse

    private var crewMembersCount:Int? =null
    private var movieCrewMembers:ArrayList<CrewBody?>? = ArrayList()

    override fun loadMovieData(movieId: Int) {
        movieInfoInteractor.getMovieCrew(movieId)
    }


    override fun setMovieInfo(movieInfo: MovieInfoResponse) {
        view?.setMovieRate(movieInfo.rating)
        view?.setMovieVoteCount(movieInfo.voteCount)
        view?.setMovieOverview(movieInfo.overview)
        view?.setMovieBackdropsCover(Constants.IMAGE_BASE_URL + movieInfo.backdropPath)
        view?.setBackdropImageListener()
        view?.setMoviePostersCover(Constants.IMAGE_BASE_URL + movieInfo.posterPath)
        view?.setPosterImageListener()
    }

    override fun setTvInfo(tvInfo: TvInfoResponse) {
        view?.setMovieRate(tvInfo.rating)
        view?.setMovieVoteCount(tvInfo.voteCount)
        view?.setMovieOverview(tvInfo.overview)
        view?.setMovieBackdropsCover(Constants.IMAGE_BASE_URL + tvInfo.backdropPath)
        view?.setBackdropImageListener()
        view?.setMoviePostersCover(Constants.IMAGE_BASE_URL + tvInfo.posterPath)
        view?.setPosterImageListener()
    }

    override fun onMovieCreditCallFinished(entertainmentCredit: EntertainmentCreditsResponse) {
        crewMembersCount = entertainmentCredit.crew?.size
        movieCrewMembers = entertainmentCredit.crew
        view?.initCrewRecyclerView()
    }

    override fun setMovieTrailers(entertainmentTrailersResponse: EntertainmentTrailersResponse?) {
        val movieTrailers = entertainmentTrailersResponse?.trailerResults
        this.movieTrailers = movieTrailers!!
        view?.initMovieTrailersRecyclerView()
    }

    override fun onFailure(throwable: Throwable) {
        view?.showFailureCause(throwable.cause.toString())
    }

    override fun setMovieImages(entertainmentImagesResponse: EntertainmentImagesResponse) {
        this.entertainmentImagesResponse = entertainmentImagesResponse
    }

    override fun getTrailersCount(): Int {
        return movieTrailers.size
    }

    override fun onBindTrailer(position: Int, trailerView: EntertainmentInfoDetailsContract.TrailerView) {
        val trailerThumbnailUrl = getVideoThumbnailUrl(movieTrailers[position].trailerKey)
        trailerView.setTrailerThumbnail(trailerThumbnailUrl)
        trailerView.setTrailerTitle(movieTrailers[position].trailerTitle)
    }

    private fun getVideoThumbnailUrl(videoKey: String?): String?{
        return Constants.IMAGE_THUMBNAIL_BASE_URL + videoKey + Constants.IMAGE_THUMBNAIL_ENDPOINT_URL
    }

    override fun onTrailerClicked(position: Int) {
        val trailerKey = movieTrailers[position].trailerKey
        val trailerUri = Uri.parse(Constants.YOUTUBE_VIDEO_BASE_URL + trailerKey)
        view?.openTrailerOnYoutube(trailerUri)
    }

    override fun handleMovieBackdropCoverClicked() {
        view?.openBackdropActivity(entertainmentImagesResponse.backdrops)
    }

    override fun handleMoviePosterClicked() {
        view?.openPostersActivity(entertainmentImagesResponse.posters)
    }

    override fun getMovieCrewMembersCount(): Int{
        return if(crewMembersCount !=null) {
            if (crewMembersCount!! > 6) {
                6
            } else{
                crewMembersCount!!
            }
        }else{
            0
        }
    }

    override fun onBindCrew(crewViewHolder: EntertainmentInfoDetailsContract.CrewRowView, position: Int) {
        crewViewHolder.setCrewMemberJobTitle(movieCrewMembers?.get(position)?.job)
        crewViewHolder.setCrewMemberName(movieCrewMembers?.get(position)?.name)
    }

    override fun attachView(view: EntertainmentInfoDetailsContract.View) {
        this.view = view
    }

    override fun deAttachView() {
        view = null
    }
}