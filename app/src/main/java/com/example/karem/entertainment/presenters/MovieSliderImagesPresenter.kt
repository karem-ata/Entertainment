package com.example.karem.entertainment.presenters

import com.example.karem.entertainment.contracts.MovieImagesSliderContract.SliderView
import com.example.karem.entertainment.contracts.MovieImagesSliderContract
import com.example.karem.entertainment.models.ImagesBody
import com.example.karem.entertainment.utils.Constants

class MovieSliderImagesPresenter: MovieImagesSliderContract.Presenter{

    private var movieImagesPath: ArrayList<String>?= null
    private var view:MovieImagesSliderContract.View?=null

    override fun setMovieImages(movieImages: ArrayList<ImagesBody>?) {
        movieImages?.add(movieImages[0])
        movieImagesPath = getMovieSliderImages(movieImages!!)
        view?.initMovieSlider()
    }

    override fun getSliderImagesCount(): Int {
        return movieImagesPath!!.size
    }

    override fun onBindSliderView(position: Int, sliderView: SliderView) {
        val imageUrl = movieImagesPath?.get(position)!!
        sliderView.setSliderImageSrc(imageUrl)
    }

    override fun getImageUrl(imageUrlKey: String): String {
        return Constants.IMAGE_BASE_URL + imageUrlKey
    }

    override fun handleCurrentPagerItem(position: Int) {
        if (position == movieImagesPath!!.size - 1) {
            view?.setCurrentItemToZero()
        }
    }

    private fun getMovieSliderImages(backdropsUrls: ArrayList<ImagesBody>): ArrayList<String>? {
        val movieSliderImagesUrl = ArrayList<String>()
        for(backdrop in backdropsUrls) {
            val backDropUrl = getImageUrl(backdrop.imagePath!!)
            movieSliderImagesUrl.add(backDropUrl)
        }
        return movieSliderImagesUrl
    }

    override fun attachView(view: MovieImagesSliderContract.View) {
        this.view = view
    }

    override fun deAttachView() {
        view = null
    }
}