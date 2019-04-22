package com.example.karem.entertainment.presenters

import com.example.karem.entertainment.contracts.EntertainmentBaseContract
import com.example.karem.entertainment.interfaccs.FragmentEffect
import com.example.karem.entertainment.interfaccs.FragmentNavigation
import com.example.karem.entertainment.models.EntertainmentCategory
import com.example.karem.entertainment.utils.Constants
import kotlin.collections.ArrayList

abstract class EntertainmentBasePresenter:
        EntertainmentBaseContract.Presenter{

    abstract fun loadData(currentCategory: EntertainmentCategory)

    abstract fun onItemClickHandler(itemPosition: Int, currentCategory: EntertainmentCategory, fragmentNavigation: FragmentNavigation?)

    protected  fun updateCategoryRecyclerView(entertainmentCategory: EntertainmentCategory, entertainmentList:ArrayList<*>) {
        hideLoadingProgressBar(entertainmentCategory)
        increaseCategoryPageNo(entertainmentCategory)
        updateCategoryList(entertainmentCategory, entertainmentList)
        updateCategoryRecyclerViewPreviousTotalItems(entertainmentCategory)
        notifyCategoryRecyclerViewAdapter(entertainmentCategory)
    }

    override fun setCurrentFragmentProperties(fragmentEffect: FragmentEffect?, fragmentTag: String?) {
        fragmentEffect?.handleFragmentEffect(fragmentTag)
    }

    private fun increaseCategoryPageNo(currentCategory: EntertainmentCategory){
        currentCategory.pageNo++
    }

    private fun updateCategoryList(entertainmentCategory: EntertainmentCategory, entertainmentList: ArrayList<*>){
        entertainmentCategory.list.addAll(entertainmentList)
    }

    private fun updateCategoryRecyclerViewPreviousTotalItems(currentCategory: EntertainmentCategory){
        currentCategory.recyclerViewPreviousTotalItems = currentCategory.list.size
    }

    private fun notifyCategoryRecyclerViewAdapter(currentCategory: EntertainmentCategory){
        currentCategory.recyclerView.adapter?.notifyDataSetChanged()
    }

    override fun getEntertainmentListCount(currentCategory: EntertainmentCategory): Int {
        return currentCategory.list.size
    }

    override fun getItemType(currentCategory: EntertainmentCategory, position: Int): Int {
        return if (currentCategory.list[position] == null){
            Constants.LINEAR_PROGRESS_TYPE
        }else{
            return Constants.ENTERTAINMENT_TYPE
        }
    }

    override fun onBindRowProgressViewAtPosition(position: Int, rowView: EntertainmentBaseContract.ProgressBarRowView) {
        rowView.setProgressBar()
    }

    protected fun showLoadingProgressBar(entertainmentCategory: EntertainmentCategory, gridLayout: Boolean = false) {
        if (gridLayout) {
            if (entertainmentCategory.pageNo > 1 && entertainmentCategory.list.size > 10) {
                entertainmentCategory.list.add(null)
                entertainmentCategory.recyclerView.adapter?.notifyItemInserted(entertainmentCategory.list.size - 1)
            }
        }else if (entertainmentCategory.pageNo > 1 && entertainmentCategory.list.size > 4 ){
            entertainmentCategory.list.add(null)
            entertainmentCategory.recyclerView.adapter?.notifyItemInserted(entertainmentCategory.list.size - 1)
        }
    }

    protected fun hideLoadingProgressBar(entertainmentCategory: EntertainmentCategory) {
        entertainmentCategory.apply {
            list.remove(null)
            recyclerView.adapter?.notifyItemRemoved(entertainmentCategory.list.size - 1)
            recyclerViewPreviousTotalItems--
        }
    }
}