package com.example.karem.entertainment.contracts

import android.net.Uri
import com.example.karem.entertainment.models.MovieInfoResponse
import com.example.karem.entertainment.models.EntertainmentImagesResponse
import com.example.karem.entertainment.models.EntertainmentTrailersResponse
import com.example.karem.entertainment.models.TvInfoResponse

interface MovieDetailsContract {

    interface SliderView {
        fun setSliderImageSource(imageUrl: String)
        fun showVideoIcon()
    }

    interface View{
        fun setActivityLayout()
        fun startDetailsActivity(entertainmentId: Int?, entertainmentType: String)
        fun openMovieTrailer(trailerUri: Uri)
        fun setEntertainmentPoster(imageUrl: String)
        fun setMovieTitle(title: String?)
        fun setEntertainmentGenre(genres: String)
        fun setCurrentItemToZero()
        fun setToolbarTitle(title: String?)
        fun initMovieBackdropsViewPager()
        fun initFragmentViewPager(movieInfo: MovieInfoResponse?, tvInfo: TvInfoResponse?, entertainmentImages: EntertainmentImagesResponse, entertainmentTrailers: EntertainmentTrailersResponse?)
    }

    interface Interactor {

        interface OnFinishedListener{
            fun onMovieDetailsCallFinished(movieInfo : MovieInfoResponse)
            fun onMovieImagesCallFinished(entertainmentImages : EntertainmentImagesResponse)
            fun onMovieVideoCallFinished(entertainmentTrailers: EntertainmentTrailersResponse?)
            fun onTvDetailsCallFinished(tvInfo: TvInfoResponse)
            fun onFailure(throwable: Throwable)
        }
        fun getMoviesDetails(movieId: Int)
        fun getMovieImages(movieId: Int)
        fun getMovieVideos(movieId: Int)
        fun getTvDetails(tvId: Int)
        fun getTvImages(tvId: Int)
        fun getTvVideos(tvId: Int)
    }

    interface Presenter{
        fun onPlayIconClickHandler()
        fun loadEntertainmentData(entertainmentId: Int, entertainmentType: String)
        fun getSliderImagesCount(): Int
        fun onBindViewAtPosition(position: Int, sliderView: MovieDetailsContract.SliderView)
        fun handleCurrentPagerItem(position: Int)
        fun setToolbarTitle()
    }
}