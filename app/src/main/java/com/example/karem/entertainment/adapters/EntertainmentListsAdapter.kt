package com.example.karem.entertainment.adapters

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import com.example.karem.entertainment.R
import com.example.karem.entertainment.contracts.EntertainmentBaseContract
import com.example.karem.entertainment.interfaccs.FragmentNavigation
import com.example.karem.entertainment.models.EntertainmentCategory
import com.example.karem.entertainment.presenters.EntertainmentBasePresenter
import com.example.karem.entertainment.utils.Constants
import com.squareup.picasso.Picasso

class EntertainmentListsAdapter(basePresenter: EntertainmentBasePresenter, private val currentCategory: EntertainmentCategory, private val fragmentFragmentNavigation: FragmentNavigation?= null):
        RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var entertainmentPresenter = basePresenter

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        val vh: RecyclerView.ViewHolder

        vh = when (viewType) {
            Constants.LINEAR_PROGRESS_TYPE -> {
                val view: View = LayoutInflater.from(parent.context).inflate(R.layout.linear_recycler_footer, parent, false)
                ProgressBarViewHolder(view)
            }
            Constants.GRID_PROGRESS_TYPE -> {
                val view: View = LayoutInflater.from(parent.context).inflate(R.layout.grid_recycler_footer, parent, false)
                ProgressBarViewHolder(view)
            }
            else -> {
                val view: View = LayoutInflater.from(parent.context).inflate(R.layout.entertainment_recycler_view_item, parent, false)
                EntertainmentViewHolder(view)
            }
        }
        return vh
    }

    override fun getItemCount(): Int {
        return entertainmentPresenter.getEntertainmentListCount(currentCategory)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is EntertainmentViewHolder) {
            entertainmentPresenter.onBindRowViewAtPosition(currentCategory, position, holder)
        } else if (holder is ProgressBarViewHolder) {
            entertainmentPresenter.onBindRowProgressViewAtPosition(position, holder)
        }
    }

    override fun getItemViewType(position: Int): Int {
        return entertainmentPresenter.getItemType(currentCategory, position)
    }

    inner class EntertainmentViewHolder(view: View) : RecyclerView.ViewHolder(view), View.OnClickListener, EntertainmentBaseContract.EntertainmentRowView {

        init { view.setOnClickListener(this) }

        private var entertainmentPosterView: ImageView = view.findViewById(R.id.entertainmentImagePoster)
        private var entertainmentTitleView: TextView = view.findViewById(R.id.entertainmentTextTitle)

        override fun onClick(view: View?) {
            entertainmentPresenter.onItemClickHandler(layoutPosition, currentCategory, fragmentFragmentNavigation)
        }


        override fun setEntertainmentPoster(entertainmentPosterUrl: String?) {
            Picasso.get().load(Constants.IMAGE_BASE_URL + entertainmentPosterUrl).into(entertainmentPosterView)
        }

        override fun setEntertainmentTitle(title: String?) {
            entertainmentTitleView.text = title
        }
    }

    inner class ProgressBarViewHolder(view: View) : RecyclerView.ViewHolder(view), EntertainmentBaseContract.ProgressBarRowView {

        private var loadingCategoryItems: ProgressBar = view.findViewById(R.id.progressBar)

        override fun setProgressBar() {
            loadingCategoryItems.isIndeterminate = true
        }
    }
}