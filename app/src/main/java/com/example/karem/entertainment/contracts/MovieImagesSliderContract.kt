package com.example.karem.entertainment.contracts

import com.example.karem.entertainment.models.ImagesBody

interface MovieImagesSliderContract {

    interface SliderView{
        fun setSliderImageSrc(imageUrl: String)
    }

    interface View{
        fun initMovieSlider()
        fun setCurrentItemToZero()
    }

    interface Presenter{
        fun attachView(view: View)
        fun deAttachView()
        fun getSliderImagesCount(): Int
        fun setMovieImages(movieImages: ArrayList<ImagesBody>?)
        fun onBindSliderView(position: Int, sliderView:SliderView)
        fun getImageUrl(imageUrlKey: String): String
        fun handleCurrentPagerItem(position: Int)
    }
}