package com.example.karem.entertainment.EntertainmentHomeFragments

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.PopupMenu
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ProgressBar
import android.widget.Toast
import com.example.karem.entertainment.R
import com.example.karem.entertainment.adapters.EntertainmentListsAdapter
import com.example.karem.entertainment.contracts.MoviesContract
import com.example.karem.entertainment.interfaccs.FragmentEffect
import com.example.karem.entertainment.interfaccs.FragmentNavigation
import com.example.karem.entertainment.models.EntertainmentCategory
import com.example.karem.entertainment.presenters.MoviesPresenter
import com.example.karem.entertainment.utils.Constants
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_movies.*

class MoviesFragment : Fragment(), MoviesContract.View {

    private lateinit var fragmentNavigation: FragmentNavigation
    private lateinit var fragmentEffect: FragmentEffect
    private val moviesPresenter = MoviesPresenter()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_movies, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        if (activity != null) {
            initMovieCategories()
            setShowMoreListener()
            moviesPresenter.setCurrentFragmentProperties(fragmentEffect, tag)
        }
    }

    override fun attachFragmentEffect(fragmentEffect: FragmentEffect) {
        this.fragmentEffect = fragmentEffect
    }

    private fun initMovieCategories() {
        initUpcomingMovieCategory()
        initNowPlayingMovieCategory()
        initTopRatedMovieCategory()
        initPopularMovieCategory()
    }

    private fun initUpcomingMovieCategory(){
        initCategory(upcomingRecyclerViewMovies, Constants.ENTERTAINMENT_UPCOMING_MOVIES, R.id.loadingUpComingMovies)
    }

    private fun initNowPlayingMovieCategory(){
        initCategory(nowPlayingRecyclerViewMovies, Constants.ENTERTAINMENT_NOW_PLAYING_MOVIES, R.id.loadingNowPlayingMovies)
    }

    private fun initTopRatedMovieCategory(){
        initCategory(topRatedRecyclerViewMovies, Constants.ENTERTAINMENT_TOP_RATED_MOVIES, R.id.loadingTopRatedMovies)
    }

    private fun initPopularMovieCategory(){
        initCategory(popularRecyclerViewMovies, Constants.ENTERTAINMENT_POPULAR_MOVIES, R.id.loadingPopularMovies)
    }

    private fun initCategory(categoryRecyclerView: RecyclerView, categoryName: String, progressBarId: Int) {
        val categoryModel = getCurrentCategoryModel(categoryName, categoryRecyclerView,progressBarId)
        initRecyclerViewLayoutManager(categoryRecyclerView)
        initRecyclerViewAdapter(categoryRecyclerView, categoryModel)
        requestData(categoryModel)
        setOnScrollListener(categoryModel)
    }

    private fun initRecyclerViewLayoutManager(categoryRecyclerView: RecyclerView){
        val linearLayoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
        categoryRecyclerView.layoutManager =  linearLayoutManager
    }

    private fun initRecyclerViewAdapter(categoryRecyclerView: RecyclerView, categoryModel: EntertainmentCategory){
        val categoryRecyclerViewAdapter = EntertainmentListsAdapter(moviesPresenter, categoryModel, fragmentNavigation)
        categoryRecyclerView.adapter = categoryRecyclerViewAdapter
    }

    private fun getCurrentCategoryModel(categoryName: String, categoryRecyclerView: RecyclerView, categoryProgressBarId: Int): EntertainmentCategory {
        return EntertainmentCategory(categoryName, categoryRecyclerView, categoryProgressBarId)
    }

    private fun requestData(currentCategory: EntertainmentCategory){
        moviesPresenter.loadData(currentCategory)
    }

    private fun setOnScrollListener(currentCategory: EntertainmentCategory) {
        val categoryRecyclerView = currentCategory.recyclerView

        categoryRecyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                if (dx > 0) {
                    val recyclerViewLayoutManager = categoryRecyclerView.layoutManager as LinearLayoutManager
                    val totalItemCount = recyclerViewLayoutManager.itemCount
                    val firstVisibleItemPosition = recyclerViewLayoutManager.findFirstVisibleItemPosition()
                    val visibleItemCount: Int = categoryRecyclerView.childCount

                    if ((visibleItemCount + firstVisibleItemPosition + Constants.threshold) >= totalItemCount)
                        if (currentCategory.loading) {
                            categoryRecyclerView.post {
                                moviesPresenter.loadData(currentCategory)
                            }
                        }
                }
            }
        })
    }

    private fun setShowMoreListener() {
         showAllNowPlayingMoviesIcon.setOnClickListener {
            showPopupMenu(showAllNowPlayingMoviesIcon, Constants.ENTERTAINMENT_NOW_PLAYING_MOVIES)
        }
        showAllUpcomingMoviesIcon.setOnClickListener {
            showPopupMenu(showAllUpcomingMoviesIcon, Constants.ENTERTAINMENT_UPCOMING_MOVIES)
        }
        showAllTopRatedMoviesIcon.setOnClickListener {
            showPopupMenu(showAllTopRatedMoviesIcon, Constants.ENTERTAINMENT_TOP_RATED_MOVIES)
        }
        showAllPopularMoviesIcon.setOnClickListener {
            showPopupMenu(showAllPopularMoviesIcon, Constants.ENTERTAINMENT_POPULAR_MOVIES)
        }
    }

    private fun showPopupMenu(view: ImageButton, moviesCategory: String) {
        val popup = PopupMenu(view.context, view)
        popup.inflate(R.menu.show_more)
        popup.setOnMenuItemClickListener(PopupMenu.OnMenuItemClickListener { item ->
            when (item?.itemId) {
                R.id.showMore -> {
                    moviesPresenter.showAllItemsFragment(fragmentNavigation, moviesCategory)
                    return@OnMenuItemClickListener true
                }
            }
            true
        })
        popup.show()
    }

    override fun attachNavigationInterface(fragmentNavigation: FragmentNavigation) {
        this.fragmentNavigation = fragmentNavigation
    }

    override fun hideProgressBar(progressBarId: Int) {
        val progressBar = view?.findViewById<ProgressBar>(progressBarId)
        progressBar?.visibility = View.GONE
    }

    override fun onPause() {
        super.onPause()
        moviesPresenter.deAttachView()
    }

    override fun onResume() {
        super.onResume()
        moviesPresenter.attachView(this)
    }

    override fun showFailureCause(failureCause: String) {
        Toast.makeText(activity, failureCause,Toast.LENGTH_SHORT).show()
    }
}