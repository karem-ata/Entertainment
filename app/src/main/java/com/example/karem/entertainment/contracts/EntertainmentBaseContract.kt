package com.example.karem.entertainment.contracts

import com.example.karem.entertainment.interfaccs.FragmentEffect
import com.example.karem.entertainment.interfaccs.FragmentNavigation
import com.example.karem.entertainment.models.EntertainmentCategory
interface EntertainmentBaseContract {


    interface View {
        fun attachFragmentEffect(fragmentEffect: FragmentEffect)
        fun hideProgressBar(progressBarId: Int)
        fun attachFragmentNavigation(fragmentNavigation: FragmentNavigation)
    }

    interface EntertainmentRowView {
        fun setEntertainmentTitle(title: String?)
        fun setEntertainmentPoster(entertainmentPosterUrl: String?)
    }

    interface ProgressBarRowView {
        fun setProgressBar()
    }

    interface Presenter{
        fun getEntertainmentListCount(currentCategory: EntertainmentCategory): Int?
        fun onBindRowViewAtPosition(currentCategory: EntertainmentCategory, position: Int, rowView: EntertainmentBaseContract.EntertainmentRowView)
        fun onBindRowProgressViewAtPosition(position: Int, rowView: EntertainmentBaseContract.ProgressBarRowView)
        fun getItemType(currentCategory: EntertainmentCategory, position: Int): Int
        fun setCurrentFragmentProperties(fragmentEffect: FragmentEffect?, fragmentTag: String?)
    }


    interface Interactor {
        interface OnFinishedListener {
            fun onEntertainmentCallFinished(entertainmentList: ArrayList<*>?, entertainmentCategory: EntertainmentCategory)
            fun onFailure(throwable: Throwable)
        }
    }
}