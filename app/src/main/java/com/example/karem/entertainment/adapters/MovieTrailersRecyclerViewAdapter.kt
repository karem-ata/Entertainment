package com.example.karem.entertainment.adapters

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.karem.entertainment.R
import com.example.karem.entertainment.contracts.EntertainmentInfoDetailsContract
import com.example.karem.entertainment.presenters.InfoFragmentPresenter
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.trailer_recycler_view_item.view.*

class MovieTrailersRecyclerViewAdapter(private val movieInfoPresenter: InfoFragmentPresenter):
        RecyclerView.Adapter<MovieTrailersRecyclerViewAdapter.ItemViewHolder>(){


    override fun onCreateViewHolder(parent: ViewGroup, p1: Int): ItemViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.trailer_recycler_view_item, parent, false)
        view.apply {
            movieTrailerIcon.background.alpha = 140
            view.movieTrailerImageView.clipToOutline = true
        }
        return ItemViewHolder(view)
    }

    override fun getItemCount(): Int {
        return movieInfoPresenter.getTrailersCount()
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        movieInfoPresenter.onBindTrailer(position, holder)
    }

    inner class ItemViewHolder(private val view: View): RecyclerView.ViewHolder(view), EntertainmentInfoDetailsContract.TrailerView{

        init { view.setOnClickListener {movieInfoPresenter.onTrailerClicked(layoutPosition)} }

        override fun setTrailerThumbnail(movieTrailerThumbnailUrl: String?) {
            Picasso.get().load(movieTrailerThumbnailUrl).into(view.movieTrailerImageView)
        }

        override fun setTrailerTitle(movieTrailerTitle: String?) {
            view.movieTrailerTitleTextView.text = movieTrailerTitle
        }
    }
}