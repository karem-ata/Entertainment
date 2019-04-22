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
import com.example.karem.entertainment.contracts.TvContract
import com.example.karem.entertainment.interfaccs.FragmentEffect
import com.example.karem.entertainment.interfaccs.FragmentNavigation
import com.example.karem.entertainment.models.EntertainmentCategory
import com.example.karem.entertainment.presenters.TvPresenter
import com.example.karem.entertainment.utils.Constants
import kotlinx.android.synthetic.main.fragment_tv.*

class TvFragment : Fragment(), TvContract.View {

    private lateinit var fragmentNavigation: FragmentNavigation
    private lateinit var fragmentEffect: FragmentEffect

    private val tvPresenter = TvPresenter()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_tv, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        if (activity != null) {
            initTvCategories()
            setShowMoreListener()
        }
    }

    override fun attachFragmentEffect(fragmentEffect: FragmentEffect) {
        this.fragmentEffect = fragmentEffect
    }

    private fun initTvCategories() {
        initOnAirTvCategory()
        initTrendingTvCategory()
        initPopularTvCategory()
        initTopRatedTvCategory()
    }

    private fun initOnAirTvCategory(){
        initCategory(onAirRecyclerViewTv, Constants.ENTERTAINMENT_ON_AIR_TV, R.id.loadingOnAirTv)
    }

    private fun initTrendingTvCategory(){
        initCategory(trendingRecyclerViewTv, Constants.ENTERTAINMENT_TRENDING_TV, R.id.loadingTrendingTv)
    }

    private fun initTopRatedTvCategory(){
        initCategory(topRatedRecyclerViewTv, Constants.ENTERTAINMENT_TOP_RATED_TV, R.id.loadingTopRatedTv)
    }

    private fun initPopularTvCategory(){
        initCategory(popularRecyclerViewTv, Constants.ENTERTAINMENT_POPULAR_TV, R.id.loadingPopularTv)
    }

    private fun initCategory(recyclerView: RecyclerView, categoryName: String, categoryProgressBarId: Int){
        val categoryModel = getCurrentCategoryModel(categoryName, recyclerView,categoryProgressBarId)
        initRecyclerViewLayoutManager(recyclerView)
        initRecyclerViewAdapter(recyclerView, categoryModel)
        requestData(categoryModel)
        setOnScrollListener(categoryModel)
    }

    private fun initRecyclerViewLayoutManager(categoryRecyclerView: RecyclerView){
        val linearLayoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
        categoryRecyclerView.layoutManager =  linearLayoutManager
    }

    private fun initRecyclerViewAdapter(categoryRecyclerView: RecyclerView, categoryModel: EntertainmentCategory){
        val categoryRecyclerViewAdapter = EntertainmentListsAdapter(tvPresenter, categoryModel, fragmentNavigation)
        categoryRecyclerView.adapter = categoryRecyclerViewAdapter
    }

    private fun getCurrentCategoryModel(categoryName: String, categoryRecyclerView: RecyclerView, categoryProgressBarId: Int): EntertainmentCategory {
        return EntertainmentCategory(categoryName, categoryRecyclerView, categoryProgressBarId)
    }

    private fun requestData(currentCategory: EntertainmentCategory){
        tvPresenter.loadData(currentCategory)
    }

    private fun setOnScrollListener(currentCategory: EntertainmentCategory) {
        var loading = true
        var previousTotal = 0
        val categoryRecyclerView = currentCategory.recyclerView

        categoryRecyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                if (dx > 0) {
                    val recyclerViewLayoutManager = recyclerView.layoutManager as LinearLayoutManager
                    val totalItemCount = recyclerViewLayoutManager.itemCount
                    val firstVisibleItemPosition = recyclerViewLayoutManager.findFirstVisibleItemPosition()
                    val visibleItemCount: Int = categoryRecyclerView.childCount

                    if (loading) {
                        if (totalItemCount > previousTotal) {
                            previousTotal = totalItemCount
                            loading = false
                        }
                    }

                    if (!loading && totalItemCount - visibleItemCount <= firstVisibleItemPosition + Constants.threshold) {
                        categoryRecyclerView.post {
                            tvPresenter.loadData(currentCategory)
                            previousTotal++
                        }
                        loading = true
                    }
                }
            }
        })
    }

    private fun setShowMoreListener() {
        showAllOnAirTv.setOnClickListener {
            showPopupMenu(showAllOnAirTv, Constants.ENTERTAINMENT_ON_AIR_TV)
        }
        showAllTrendingTv.setOnClickListener {
            showPopupMenu(showAllTrendingTv, Constants.ENTERTAINMENT_TRENDING_TV)
        }
        showAllTopRatedTv.setOnClickListener {
            showPopupMenu(showAllTopRatedTv, Constants.ENTERTAINMENT_TOP_RATED_TV)
        }
        showAllPopularTv.setOnClickListener {
            showPopupMenu(showAllPopularTv, Constants.ENTERTAINMENT_POPULAR_TV)
        }
    }

    private fun showPopupMenu(view: ImageButton, moviesCategory: String) {
        val popup = PopupMenu(view.context, view)
        popup.inflate(R.menu.show_more)
        popup.setOnMenuItemClickListener(PopupMenu.OnMenuItemClickListener { item ->
            when (item?.itemId) {
                R.id.showMore -> {
                    tvPresenter.showAllItemsFragment(fragmentNavigation, moviesCategory)
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
        tvPresenter.attachView(this)
    }

    override fun onResume() {
        super.onResume()
        tvPresenter.attachView(this)
    }

    override fun showFailureCause(failureCause: String) {
        Toast.makeText(activity, failureCause, Toast.LENGTH_SHORT).show()
    }
}