package com.example.karem.entertainment.adapters

import android.content.Context
import android.support.constraint.ConstraintLayout
import android.support.v4.view.PagerAdapter
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.example.karem.entertainment.R
import com.example.karem.entertainment.contracts.MovieImagesSliderContract
import com.example.karem.entertainment.presenters.MovieSliderImagesPresenter
import com.squareup.picasso.Picasso

class FullSliderAdapter(private val context: Context, private val movieSliderImagesPresenter: MovieSliderImagesPresenter): PagerAdapter(), MovieImagesSliderContract.SliderView {

    private lateinit var imageSlider:ImageView

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view == `object`
    }

    override fun getCount(): Int {
        return movieSliderImagesPresenter.getSliderImagesCount()
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val layoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val view = layoutInflater.inflate(R.layout.full_slider_layout, container, false)
        imageSlider = view.findViewById(R.id.backdropImageView)
        movieSliderImagesPresenter.onBindSliderView(position, this)
        container.addView(view)
        return view
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as ConstraintLayout)
    }

    override fun setSliderImageSrc(imageUrl: String) {
        Picasso.get().load(imageUrl).into(imageSlider)
    }
}