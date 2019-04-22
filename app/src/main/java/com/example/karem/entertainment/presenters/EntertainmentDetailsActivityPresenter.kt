package com.example.karem.entertainment.presenters

import android.net.Uri
import android.util.Log
import com.example.karem.entertainment.contracts.MovieDetailsContract
import com.example.karem.entertainment.interactors.MovieDetailsInteractor
import com.example.karem.entertainment.interfaccs.FragmentNavigation
import com.example.karem.entertainment.models.*
import com.example.karem.entertainment.utils.Constants

class EntertainmentDetailsActivityPresenter(private val view: MovieDetailsContract.View): MovieDetailsContract.Presenter,
        MovieDetailsContract.Interactor.OnFinishedListener, FragmentNavigation {

    private val entertainmentInteractor = MovieDetailsInteractor(this)
    private var movieSliderImagesUrl: ArrayList<String?>? = null

    private var isEntertainmentInfoResponseFinished = false
    private var isEntertainmentVideosResponseFinished = false
    private var isEntertainmentImagesResponseFinished = false

    private var movieInfo: MovieInfoResponse?= null
    private var tvInfo: TvInfoResponse?=  null
    private lateinit var entertainmentImages: EntertainmentImagesResponse
    private var entertainmentTrailers: EntertainmentTrailersResponse? = null

    private var entertainmentTrailerKey: String? = null

    override fun onTvDetailsCallFinished(tvInfo: TvInfoResponse) {
        this.tvInfo = tvInfo
        isEntertainmentInfoResponseFinished = true
        initFragmentViewPager()
        val imageUrlKey = this.tvInfo?.posterPath
        val imageUrl = getImageUrl(imageUrlKey)
        val genre = getMovieGenres(this.tvInfo?.genres!!)
        Log.e("ttt", tvInfo.originalTitle)

        view.apply {
            setEntertainmentGenre(genre)
            setEntertainmentPoster(imageUrl)
            setMovieTitle(this@EntertainmentDetailsActivityPresenter.tvInfo?.title)
        }
    }

    override fun loadEntertainmentData(entertainmentId: Int, entertainmentType: String) {
        when (entertainmentType) {
            Constants.MOVIE_ENTERTAINMENT -> {
                entertainmentInteractor.apply {
                    getMoviesDetails(entertainmentId)
                    getMovieImages(entertainmentId)
                    getMovieVideos(entertainmentId)
                }
            }
            Constants.TV_ENTERTAINMENT -> {
                entertainmentInteractor.apply {
                    getTvDetails(entertainmentId)
                    getTvImages(entertainmentId)
                    getTvVideos(entertainmentId)
                }
            }
        }
    }

    override fun getSliderImagesCount(): Int {
        return movieSliderImagesUrl!!.size
    }

    override fun onMovieDetailsCallFinished(movieInfo: MovieInfoResponse) {
        this.movieInfo = movieInfo
        isEntertainmentInfoResponseFinished = true
        initFragmentViewPager()
        val imageUrlKey = movieInfo.posterPath
        val imageUrl = getImageUrl(imageUrlKey)
        val genre = getMovieGenres(movieInfo.genres!!)

        view.apply {
            setEntertainmentGenre(genre)
            setEntertainmentPoster(imageUrl)
            setMovieTitle(movieInfo.title!!)
        }
    }

    private fun getImageUrl(imageUrl: String?): String {
        return Constants.IMAGE_BASE_URL + imageUrl
    }

    private fun getMovieGenres(genres: ArrayList<GenreBody>): String {
        val movieGenres = StringBuilder()
        for ((index, genre) in genres.withIndex()) {
            if (index < 4) {
                if (index != 0) {
                    movieGenres.append(", " + genre.genreName)
                } else {
                    movieGenres.append(genre.genreName)
                }
            }
        }
        return movieGenres.toString()
    }

    override fun onFailure(throwable: Throwable) {

    }

    override fun onMovieImagesCallFinished(entertainmentImages: EntertainmentImagesResponse) {
        this.entertainmentImages = entertainmentImages
        isEntertainmentImagesResponseFinished = true

        initFragmentViewPager()

        movieSliderImagesUrl = getMovieSliderImages(entertainmentImages.backdrops!!)
        if (movieSliderImagesUrl != null && movieSliderImagesUrl!!.size > 0) {
            movieSliderImagesUrl?.add(movieSliderImagesUrl?.get(0))
            setViewPager()
        }
    }

    override fun startDetailsActivity(entertainmentId: Int?, entertainmentType: String) {
        view.startDetailsActivity(entertainmentId, entertainmentType)
    }

    private fun getMovieSliderImages(backdropsUrls: ArrayList<ImagesBody>): ArrayList<String?> {
        val movieSliderImagesUrl = ArrayList<String?>()
        for ((index, movieBackdrop) in backdropsUrls.withIndex()) {
            if (index < 12) {
                val backDropUrl = getImageUrl(movieBackdrop.imagePath)
                movieSliderImagesUrl.add(backDropUrl)
            }
        }
        return movieSliderImagesUrl
    }

    override fun onBindViewAtPosition(position: Int, sliderView: MovieDetailsContract.SliderView) {
        if (position == 0) {
            sliderView.showVideoIcon()
        }
        if (movieSliderImagesUrl != null)
            sliderView.setSliderImageSource(movieSliderImagesUrl?.get(position)!!)
    }

    override fun handleCurrentPagerItem(position: Int) {
        if (position == movieSliderImagesUrl!!.size - 1) {
            view.setCurrentItemToZero()
        }
    }

    override fun onMovieVideoCallFinished(entertainmentTrailers: EntertainmentTrailersResponse?) {
        this.entertainmentTrailers = entertainmentTrailers
        isEntertainmentVideosResponseFinished = true
        initFragmentViewPager()
        entertainmentTrailerKey = entertainmentTrailers?.trailerResults?.get(0)?.trailerKey
    }

    override fun onPlayIconClickHandler() {
        val trailerUri = Uri.parse(Constants.YOUTUBE_VIDEO_BASE_URL + entertainmentTrailerKey)
        view.openMovieTrailer(trailerUri)
    }

    private fun setViewPager() {
        view.initMovieBackdropsViewPager()
    }

    private fun initFragmentViewPager() {
        if (isMovieDetailsCallsFinished()) {
            if (movieInfo != null)
                view.initFragmentViewPager(movieInfo, null, entertainmentImages, entertainmentTrailers)
            else {
                view.initFragmentViewPager(null, tvInfo, entertainmentImages, entertainmentTrailers)
            }
        }
    }

    override fun setToolbarTitle() {
        if (movieInfo!= null)
            view.setToolbarTitle(movieInfo?.title)
        else
            view.setToolbarTitle(tvInfo?.title)
    }

    private fun isMovieDetailsCallsFinished(): Boolean{
        return (isEntertainmentImagesResponseFinished && isEntertainmentVideosResponseFinished && isEntertainmentInfoResponseFinished)
    }
}