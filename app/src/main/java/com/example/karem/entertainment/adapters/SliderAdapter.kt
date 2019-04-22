package com.example.karem.entertainment.adapters

import android.content.Context
import android.support.constraint.ConstraintLayout
import android.support.v4.view.PagerAdapter
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.example.karem.entertainment.R
import com.example.karem.entertainment.contracts.MovieDetailsContract
import com.example.karem.entertainment.presenters.EntertainmentDetailsActivityPresenter
import com.squareup.picasso.Picasso

class SliderAdapter(val context: Context, private val movieDetailsActivityPresenter: EntertainmentDetailsActivityPresenter): PagerAdapter(), MovieDetailsContract.SliderView{

    private lateinit var playIcon: ImageView
    private lateinit var imageSlider:ImageView

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view == `object`
    }

    override fun getCount(): Int {
        return movieDetailsActivityPresenter.getSliderImagesCount()
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val layoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val view = layoutInflater.inflate(R.layout.slider_layout, container, false)

        imageSlider = view.findViewById(R.id.backdropImageView)

        playIcon = view.findViewById(R.id.moviePlayIcon)
        playIcon.apply {
            setOnClickListener { movieDetailsActivityPresenter.onPlayIconClickHandler() }
            background.alpha = 140
        }

        movieDetailsActivityPresenter.onBindViewAtPosition(position, this)

        container.addView(view)

        return view
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as ConstraintLayout)
    }

    override fun setSliderImageSource(imageUrl: String) {
        Picasso.get().load(imageUrl).into(imageSlider)
    }

    override fun showVideoIcon() { playIcon.visibility = View.VISIBLE }
}