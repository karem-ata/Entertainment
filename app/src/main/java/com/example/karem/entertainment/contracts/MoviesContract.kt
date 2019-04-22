package com.example.karem.entertainment.contracts

import com.example.karem.entertainment.interfaccs.FragmentEffect
import com.example.karem.entertainment.interfaccs.FragmentNavigation
import com.example.karem.entertainment.models.EntertainmentCategory

interface MoviesContract {

    interface View{
        fun attachFragmentEffect(fragmentEffect: FragmentEffect)
        fun hideProgressBar(progressBarId: Int)
        fun attachNavigationInterface(fragmentNavigation: FragmentNavigation)
        fun showFailureCause(failureCause: String)
    }

    interface Presenter{
        fun attachView(view: MoviesContract.View)
        fun deAttachView()
        fun showAllItemsFragment(fragmentNavigation: FragmentNavigation, moviesCategoryName: String)
    }

    interface Interactor {
        fun getCategoryMoviesList(currentCategory: EntertainmentCategory)
    }
}