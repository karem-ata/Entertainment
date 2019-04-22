package com.example.karem.entertainment.contracts

import android.support.v4.app.Fragment

interface EntertainmentActivityContract {

    interface View{
        fun handleNavigationItemColor(index: Int)
        fun setToolBarTitle(title: String)
        fun loadFragment(fragment: Fragment, fragmentTag: String)
        fun hideNavigationView()
        fun startDetailsActivity(movieId: Int?, entertainmentType: String)
    }

    interface Presenter{
        fun handleBottomNavigationView(itemId: Int)
        fun handleSearchViewClicked(query: String)
    }
}