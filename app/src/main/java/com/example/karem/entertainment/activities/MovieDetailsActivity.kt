package com.example.karem.entertainment.activities

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.view.ViewPager
import com.example.karem.entertainment.R
import com.example.karem.entertainment.adapters.SliderAdapter
import com.example.karem.entertainment.contracts.MovieDetailsContract
import com.example.karem.entertainment.presenters.EntertainmentDetailsActivityPresenter
import com.example.karem.entertainment.utils.Constants
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_movies_details.*
import android.support.design.widget.AppBarLayout
import com.example.karem.entertainment.adapters.FragmentViewPagerAdapter
import android.content.Intent
import android.net.Uri
import com.example.karem.entertainment.models.MovieInfoResponse
import com.example.karem.entertainment.models.EntertainmentImagesResponse
import com.example.karem.entertainment.models.EntertainmentTrailersResponse
import com.example.karem.entertainment.models.TvInfoResponse

class MovieDetailsActivity : AppCompatActivity(), MovieDetailsContract.View {

    private var entertainmentId = 0
    private var entertainmentType:String?= null
    private val entertainmentDetailsActivityPresenter: EntertainmentDetailsActivityPresenter = EntertainmentDetailsActivityPresenter(this)
    private lateinit var sliderAdapter: SliderAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.loading_layout)
        setEntertainmentDetailsArguments()
        requestData()
    }

    private fun setEntertainmentDetailsArguments() {
        val intent = intent
        intent.apply {
            entertainmentId = intent.getIntExtra(Constants.MOVIE_ID_INTENT_FLAG, 0)
            entertainmentType = intent.getStringExtra(Constants.ENTERTAINMENT_TYPE_KEY)
        }
    }

    private fun requestData() {
        entertainmentDetailsActivityPresenter.loadEntertainmentData(entertainmentId, entertainmentType!!)
    }

    override fun initMovieBackdropsViewPager() {
        attachAdapterToMovieBackdropsViewPager()
        attachTabLayoutToMovieBackdropsViewPager()
        setupViewPagerListener()
    }

    private fun attachAdapterToMovieBackdropsViewPager() {
        sliderAdapter = SliderAdapter(this, entertainmentDetailsActivityPresenter)
        moviesDetailsSliderViewPager.adapter = sliderAdapter
    }

    private fun attachTabLayoutToMovieBackdropsViewPager() {
        movieDetailsSliderTabLayout.setupWithViewPager(moviesDetailsSliderViewPager)
        removeLastTab()
    }

    private fun removeLastTab() {
        movieDetailsSliderTabLayout.removeTabAt(entertainmentDetailsActivityPresenter.getSliderImagesCount() - 1)
    }


    private fun setupViewPagerListener() {
        moviesDetailsSliderViewPager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {

            }

            override fun onPageScrollStateChanged(position: Int) {

            }

            override fun onPageSelected(position: Int) {
                entertainmentDetailsActivityPresenter.handleCurrentPagerItem(position)
            }
        })
    }

    override fun setActivityLayout() {
        setContentView(R.layout.activity_movies_details)
        setAppBarLayoutListener()
    }

    override fun setEntertainmentPoster(imageUrl: String) {
        Picasso.get().load(imageUrl).into(movieProfileImageView)
    }

    override fun setMovieTitle(title: String?) {
        movieTitleTextView.text = title
    }

    override fun setEntertainmentGenre(genres: String) {
        movieGenreTextview.text = genres
    }

    override fun setCurrentItemToZero() {
        moviesDetailsSliderViewPager.setCurrentItem(0, false)
    }

    private fun setAppBarLayoutListener() {
        var isShow = true
        moviesDetailsAppBarLayout.addOnOffsetChangedListener(object : AppBarLayout.OnOffsetChangedListener {
            private var scrollRange = -1

            override fun onOffsetChanged(appBarLayout: AppBarLayout, verticalOffset: Int) {
                if (scrollRange == -1) {
                    scrollRange = appBarLayout.totalScrollRange
                }
                if (scrollRange + verticalOffset in -100..100) {
                    entertainmentDetailsActivityPresenter.setToolbarTitle()
                    isShow = true
                } else if (isShow) {
                    moviesDetailsCollapsingLayout.title = ""
                    isShow = false
                }
            }
        })
    }

    override fun setToolbarTitle(title: String?) {
        moviesDetailsCollapsingLayout.title = title
    }

    override fun initFragmentViewPager(movieInfo: MovieInfoResponse?, tvInfo: TvInfoResponse?, entertainmentImages: EntertainmentImagesResponse, entertainmentTrailers: EntertainmentTrailersResponse?) {

        movieDetailsFragmentsViewPager.apply {
            adapter = FragmentViewPagerAdapter(supportFragmentManager, entertainmentId, movieInfo, tvInfo, entertainmentImages, entertainmentTrailers, entertainmentDetailsActivityPresenter)
            offscreenPageLimit = 3
        }

        movieDetailsFragmentsTabLayout.apply {
            setupWithViewPager(movieDetailsFragmentsViewPager)
        }
    }

    override fun openMovieTrailer(trailerUri: Uri) {
        val videoClient = Intent(Intent.ACTION_VIEW).apply {
            data = trailerUri
        }
        startActivityForResult(videoClient, 1234)
    }

    override fun startDetailsActivity(entertainmentId: Int?, entertainmentType: String) {
        val intent = Intent(this, MovieDetailsActivity::class.java)
        intent.apply {
            putExtra(Constants.MOVIE_ID_INTENT_FLAG, entertainmentId)
            putExtra(Constants.ENTERTAINMENT_TYPE_KEY, entertainmentType)
        }
        startActivity(intent)
    }
}