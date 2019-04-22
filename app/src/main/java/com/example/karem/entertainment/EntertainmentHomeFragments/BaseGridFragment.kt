package com.example.karem.entertainment.EntertainmentHomeFragments

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.widget.NestedScrollView
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.Menu
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import com.example.karem.entertainment.R
import com.example.karem.entertainment.adapters.EntertainmentListsAdapter
import com.example.karem.entertainment.contracts.EntertainmentBaseContract
import com.example.karem.entertainment.interfaccs.FragmentEffect
import com.example.karem.entertainment.interfaccs.FragmentNavigation
import com.example.karem.entertainment.models.EntertainmentCategory
import com.example.karem.entertainment.presenters.GridPresenter
import com.example.karem.entertainment.utils.Constants
import kotlinx.android.synthetic.main.fragment_all_items.*

class BaseGridFragment : Fragment(), EntertainmentBaseContract.View {

    private var categoryName: String? = null
    private var categoryType: String? = null
    private var movieId: Int?= null

    private lateinit var allItemsCategory: EntertainmentCategory
    private lateinit var gridPresenter: GridPresenter

    private lateinit var recyclerViewLayoutManager: GridLayoutManager
    private lateinit var categoryAdapter:EntertainmentListsAdapter

    private var fragmentEffect: FragmentEffect?= null
    private lateinit var fragmentNavigation: FragmentNavigation

    override fun onPrepareOptionsMenu(menu: Menu){
        menu.findItem(R.id.action_search).isVisible = false
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_all_items, container, false)
    }

    override fun attachFragmentEffect(fragmentEffect: FragmentEffect) {
        this.fragmentEffect = fragmentEffect
    }

    override fun attachFragmentNavigation(fragmentNavigation: FragmentNavigation) {
        this.fragmentNavigation = fragmentNavigation
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setHasOptionsMenu(true)
        setCategoryArguments(this.arguments)
        initPresenter()
        initRecyclerView()
        requestData(allItemsCategory)
        setViewPagerScrollListener(allItemsCategory)
        gridPresenter.setCurrentFragmentProperties(fragmentEffect, tag)
    }

    private fun setCategoryArguments(bundle: Bundle?) {
        bundle?.apply {
            categoryName = getString(Constants.CATEGORY_NAME_TAG)
            categoryType = getString(Constants.CATEGORY_TYPE_TAG)
            movieId = getInt(Constants.ENTERTAINMENT_ID_BUNDLE_KEY)
        }
    }

    private fun initPresenter() {
        gridPresenter = GridPresenter(categoryType!!)
    }

    private fun initRecyclerView() {
        initEntertainmentCategory()
        initRecyclerViewLayoutManager(categoryRecyclerView)
        categoryRecyclerView.isNestedScrollingEnabled = false
        initRecyclerViewAdapter()
    }

    private fun initRecyclerViewLayoutManager(recyclerView: RecyclerView?) {
        recyclerViewLayoutManager = GridLayoutManager(activity, 3)
        recyclerView?.layoutManager = recyclerViewLayoutManager
    }

    private fun initRecyclerViewAdapter() {
        categoryAdapter = EntertainmentListsAdapter(gridPresenter, allItemsCategory, fragmentNavigation)
        categoryRecyclerView?.adapter = categoryAdapter
    }

    private fun initEntertainmentCategory() {
        allItemsCategory = EntertainmentCategory(categoryName!!, categoryRecyclerView, R.id.loadingCategoryList, movieId)
    }

    private fun requestData(allItemsCategory: EntertainmentCategory) {
        gridPresenter.loadData(allItemsCategory)
    }

    private fun setViewPagerScrollListener(currentCategory: EntertainmentCategory) {
        var previousTotalItemCount = 0
        var loading = true
        val visibleThresholdDistance = 300

        allItemsNestedScrollLayout.setOnScrollChangeListener(NestedScrollView.OnScrollChangeListener { p0, p1, p2, p3, p4 ->
            val view = allItemsNestedScrollLayout.getChildAt(allItemsNestedScrollLayout.childCount - 1)
            val distanceToEnd = (view.bottom - (allItemsNestedScrollLayout.height + allItemsNestedScrollLayout.scrollY))

            val totalItemCount = categoryRecyclerView.layoutManager?.itemCount

            if (totalItemCount!! < previousTotalItemCount) {
                previousTotalItemCount = totalItemCount
                if (totalItemCount == 0) {
                    loading = true
                }
            }

            if (loading && (totalItemCount > previousTotalItemCount)) {
                loading = false
                previousTotalItemCount = totalItemCount
            }

              if (!loading && distanceToEnd <= visibleThresholdDistance) {
                gridPresenter.loadData(currentCategory)
                setProgressSize()
                previousTotalItemCount++
                loading = true
            }
        })
    }
    
    private fun setProgressSize() {
        recyclerViewLayoutManager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
            override fun getSpanSize(position: Int): Int {
                return when (categoryAdapter.getItemViewType(position)) {
                    Constants.ENTERTAINMENT_TYPE -> 1
                    Constants.GRID_PROGRESS_TYPE -> 3
                    Constants.LINEAR_PROGRESS_TYPE -> 1
                    else -> -1
                }
            }
        }
    }

    override fun hideProgressBar(progressBarId: Int) {
        val progressBar = view?.findViewById<ProgressBar>(progressBarId)
        progressBar?.visibility = View.GONE
    }

    override fun onPause() {
        super.onPause()
        gridPresenter.deAttachView()
    }

    override fun onResume() {
        super.onResume()
        gridPresenter.attachView(this)
    }
}