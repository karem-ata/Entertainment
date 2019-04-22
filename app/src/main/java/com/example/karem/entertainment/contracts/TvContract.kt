package com.example.karem.entertainment.contracts

import com.example.karem.entertainment.interfaccs.FragmentEffect
import com.example.karem.entertainment.interfaccs.FragmentNavigation
import com.example.karem.entertainment.models.EntertainmentCategory

interface TvContract {

    interface View{
        fun attachFragmentEffect(fragmentEffect: FragmentEffect)
        fun attachNavigationInterface(fragmentNavigation: FragmentNavigation)
        fun hideProgressBar(progressBarId: Int)
        fun showFailureCause(failureCause: String)
    }

    interface Presenter{
        fun showAllItemsFragment(fragmentNavigation: FragmentNavigation, moviesCategoryName: String)
        fun attachView(view: TvContract.View)
        fun deAttachView()
    }

    interface Interactor {
        fun getTvCategoryList(currentCategory: EntertainmentCategory)
    }
}