package com.example.karem.entertainment.presenters

import android.os.Bundle
import android.support.v4.app.Fragment
import com.example.karem.entertainment.R
import com.example.karem.entertainment.contracts.EntertainmentActivityContract
import com.example.karem.entertainment.EntertainmentHomeFragments.BaseGridFragment
import com.example.karem.entertainment.EntertainmentHomeFragments.MoviesFragment
import com.example.karem.entertainment.EntertainmentHomeFragments.TvFragment
import com.example.karem.entertainment.interfaccs.FragmentEffect
import com.example.karem.entertainment.interfaccs.FragmentNavigation
import com.example.karem.entertainment.utils.Constants

open class EntertainmentActivityPresenter(val view: EntertainmentActivityContract.View): EntertainmentActivityContract.Presenter,
        FragmentNavigation, FragmentEffect{

    private lateinit var currentEntertainment: String
    private lateinit var query: String
    private lateinit var categoryName: String

    override fun handleBottomNavigationView(itemId: Int) {
        when (itemId) {
            R.id.moviesNavigationbar -> {
                setCurrentEntertainmentType(Constants.MOVIE_ENTERTAINMENT)
                setToolBarTitle("Movies")
                loadMoviesFragment()
            }

            R.id.tvNavigationbar -> {
                setCurrentEntertainmentType(Constants.TV_ENTERTAINMENT)
                setToolBarTitle("Tv")
                loadTvFragment()
            }
        }
    }

    private fun setCurrentEntertainmentType(currentType: String){
        currentEntertainment = currentType
    }

    private fun setToolBarTitle(title: String){
        view.setToolBarTitle(title)
    }

    private fun loadMoviesFragment(){
        view.loadFragment(MoviesFragment(), Constants.MOVIE_FRAGMENT_TAG)
    }

    private fun loadTvFragment(){
        view.loadFragment(TvFragment(), Constants.TV_FRAGMENT_TAG)
    }

    private fun loadAllItemsFragment(allItemsFragment: Fragment){
        view.loadFragment(allItemsFragment, Constants.ALL_ITEMS_FRAGMENT_TAG)
    }

    private fun hideNavigationView(){
        view.hideNavigationView()
    }

    override fun navigateToAllItemsFragment(entertainmentCategoryName: String, entertainmentCategoryType: String) {
        val allItemsFragment = BaseGridFragment()
        allItemsFragment.attachFragmentNavigation(this)
        this.categoryName = entertainmentCategoryName
        
        val bundle = Bundle().apply {
            putCharSequence(Constants.CATEGORY_NAME_TAG, entertainmentCategoryName)
            putCharSequence(Constants.CATEGORY_TYPE_TAG, entertainmentCategoryType)
       }
        allItemsFragment.arguments = bundle
        loadAllItemsFragment(allItemsFragment)
        setToolBarTitle(entertainmentCategoryName)
        hideNavigationView()
    }

    override fun startDetailsActivity(entertainmentId: Int?, entertainmentType: String) {
        view.startDetailsActivity(entertainmentId, entertainmentType)
    }

    override fun handleSearchViewClicked(query: String) {
        this.query = query
        val searchFragment = BaseGridFragment()
        searchFragment.attachFragmentNavigation(this)
        val bundle = Bundle().apply {
            putCharSequence(Constants.CATEGORY_TYPE_TAG, currentEntertainment)
            putCharSequence(Constants.CATEGORY_NAME_TAG, query)
        }
        searchFragment.arguments = bundle
        hideNavigationView()
        setToolBarTitle(query)
        view.loadFragment(searchFragment, Constants.SEARCH_FRAGMENT_TAG)
    }

    override fun handleFragmentEffect(fragmentTag: String?) {
        when (fragmentTag){
            Constants.MOVIE_FRAGMENT_TAG -> {
                setToolBarTitle("Movies")
                view.handleNavigationItemColor(0)
            }
            Constants.TV_FRAGMENT_TAG -> {
                setToolBarTitle("Tv")
                view.handleNavigationItemColor(1)
            }
            Constants.SEARCH_FRAGMENT_TAG -> {
                setToolBarTitle(query)
                hideNavigationView()
                handleBottomNavigationView(1)
            }
            Constants.ALL_ITEMS_FRAGMENT_TAG -> {
                hideNavigationView()
                setToolBarTitle(categoryName)
            }
        }
    }
}