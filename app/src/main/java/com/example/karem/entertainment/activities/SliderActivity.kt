package com.example.karem.entertainment.activities

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.view.ViewPager
import com.example.karem.entertainment.R
import com.example.karem.entertainment.adapters.FullSliderAdapter
import com.example.karem.entertainment.contracts.MovieImagesSliderContract
import com.example.karem.entertainment.presenters.MovieSliderImagesPresenter
import com.example.karem.entertainment.utils.Constants
import kotlinx.android.synthetic.main.activity_backdrops.*
import android.content.pm.ActivityInfo
import android.view.WindowManager
import com.example.karem.entertainment.models.ImagesBody

class SliderActivity : AppCompatActivity(),MovieImagesSliderContract.View {

    private var moviePosters: ArrayList<ImagesBody>?= null
    private var movieBackdrops: ArrayList<ImagesBody>?= null

    private var activityOrientation:String?=null

    private lateinit var movieImagesPresenter:MovieSliderImagesPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_backdrops)
        getSliderIntent()
        setActivityOrientation()
        setWindowFlag()
        initPresenter()
    }

    private fun setActivityOrientation(){
        when(activityOrientation){
            Constants.LANDSCAPE_ORIENTATION -> requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
            Constants.PORTRAIT_ORIENTATION -> requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        }
    }

    private fun setWindowFlag(){
        window.setFlags(
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)
    }

    private fun getSliderIntent(){
        val intent = intent
        intent.apply {
            moviePosters = getSerializableExtra(Constants.MOVIE_POSTERS_INTENT_FLAG) as ArrayList<ImagesBody>?
            movieBackdrops = getSerializableExtra(Constants.MOVIE_BACKDROPS_INTENT_FLAG) as ArrayList<ImagesBody>?
            activityOrientation = getStringExtra(Constants.ORIENTATION_INTENT_FLAG)

        }
    }

    private fun initPresenter(){
        movieImagesPresenter  = MovieSliderImagesPresenter()
    }

    private fun sendMovieImages(){
        movieImagesPresenter.setMovieImages(moviePosters ?: movieBackdrops)
    }

    override fun initMovieSlider() {
        val movieImagesSlider = FullSliderAdapter(this, movieImagesPresenter)
        backdropViewPager.adapter = movieImagesSlider
        setupViewPagerListener()
    }

    private fun setupViewPagerListener() {
        backdropViewPager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {}

            override fun onPageScrollStateChanged(position: Int) {}

            override fun onPageSelected(position: Int) {
                movieImagesPresenter.handleCurrentPagerItem(position)
            }
        })
    }

    override fun setCurrentItemToZero() {
        backdropViewPager.setCurrentItem(0, false)
    }

    override fun onPause() {
        super.onPause()
        movieImagesPresenter.deAttachView()
    }

    override fun onResume() {
        super.onResume()
        movieImagesPresenter.attachView(this)
        sendMovieImages()
    }
}