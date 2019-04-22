package com.example.karem.entertainment.contracts

import android.net.Uri
import com.example.karem.entertainment.models.*

interface EntertainmentInfoDetailsContract {

    interface View{
        fun showFailureCause(failureCause: String)
        fun setMovieRate(rate: Float?)
        fun setMovieVoteCount(voteCount: Int?)
        fun setMoviePostersCover(imageUrl: String?)
        fun setMovieBackdropsCover(imageUrl: String?)
        fun setMovieOverview(overview: String?)
        fun openTrailerOnYoutube(trailerUri: Uri)
        fun initMovieTrailersRecyclerView()
        fun openBackdropActivity(movieBackdrops: ArrayList<ImagesBody>?)
        fun setBackdropImageListener()
        fun setPosterImageListener()
        fun openPostersActivity(moviePosters: ArrayList<ImagesBody>?)
        fun initCrewRecyclerView()
    }

    interface TrailerView{
        fun setTrailerThumbnail(movieTrailerThumbnailUrl: String?)
        fun setTrailerTitle(movieTrailerTitle: String?)
    }

    interface CrewRowView{
        fun setCrewMemberJobTitle(crewMemberJobTitle: String?)
        fun setCrewMemberName(crewMemberName: String?)
    }

    interface Presenter{
        fun loadMovieData(movieId: Int)
        fun getTrailersCount(): Int
        fun onBindTrailer(position: Int, trailerView: TrailerView)
        fun onTrailerClicked(position: Int)
        fun handleMovieBackdropCoverClicked()
        fun handleMoviePosterClicked()
        fun getMovieCrewMembersCount():Int
        fun onBindCrew(crewViewHolder: CrewRowView, position: Int)
        fun setMovieInfo(movieInfo: MovieInfoResponse)
        fun setTvInfo(tvInfo: TvInfoResponse)
        fun setMovieTrailers(entertainmentTrailersResponse: EntertainmentTrailersResponse?)
        fun setMovieImages(entertainmentImagesResponse: EntertainmentImagesResponse)
        fun attachView(view: View)
        fun deAttachView()
    }

    interface Interactor{
        interface OnFinishedListener{
            fun onMovieCreditCallFinished(entertainmentCredit: EntertainmentCreditsResponse)
            fun onFailure(throwable: Throwable)
        }
        fun getMovieCrew(movieId: Int)
    }
}