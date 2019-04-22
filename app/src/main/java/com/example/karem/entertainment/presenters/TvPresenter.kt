package com.example.karem.entertainment.presenters

import com.example.karem.entertainment.contracts.EntertainmentBaseContract
import com.example.karem.entertainment.contracts.TvContract
import com.example.karem.entertainment.interactors.TvInteractor
import com.example.karem.entertainment.interfaccs.FragmentEffect
import com.example.karem.entertainment.interfaccs.FragmentNavigation
import com.example.karem.entertainment.models.EntertainmentCategory
import com.example.karem.entertainment.models.TvBody
import com.example.karem.entertainment.utils.Constants
import kotlin.collections.ArrayList

class TvPresenter : EntertainmentBasePresenter(),
        TvContract.Presenter, EntertainmentBaseContract.Interactor.OnFinishedListener {

    private var view:TvContract.View?= null
    private var tvInteractor: TvInteractor = TvInteractor(this)

    override fun onEntertainmentCallFinished(entertainmentList: ArrayList<*>?, entertainmentCategory: EntertainmentCategory) {
        entertainmentList as ArrayList<TvBody>?
        if (entertainmentList != null) {
            updateCategoryRecyclerView(entertainmentCategory, entertainmentList)
            when {
                (entertainmentList.size < 1) -> loadData(entertainmentCategory)
                (entertainmentCategory.list.size < 5) -> loadData(entertainmentCategory)
                else -> view?.hideProgressBar(entertainmentCategory.progressBarId)
            }
        }
    }

    override fun setCurrentFragmentProperties(fragmentEffect: FragmentEffect?, fragmentTag: String?) {
        fragmentEffect?.handleFragmentEffect(fragmentTag)
    }

    override fun onFailure(throwable: Throwable) {
        view?.showFailureCause(throwable.cause.toString())
    }

    override fun loadData(currentCategory: EntertainmentCategory) {
        showLoadingProgressBar(currentCategory)
        tvInteractor.getTvCategoryList(currentCategory)
    }

    override fun onBindRowViewAtPosition(currentCategory: EntertainmentCategory, position: Int, rowView: EntertainmentBaseContract.EntertainmentRowView) {
        val tv = getTvShow(currentCategory, position)
        rowView.setEntertainmentTitle(tv.entertainmentTitle)
        rowView.setEntertainmentPoster(tv.entertainmentPoster)
    }

    override fun showAllItemsFragment(fragmentNavigation: FragmentNavigation, moviesCategoryName: String) {
        fragmentNavigation.navigateToAllItemsFragment(moviesCategoryName, Constants.TV_ENTERTAINMENT)
    }


    override fun onItemClickHandler(itemPosition: Int, currentCategory: EntertainmentCategory, fragmentNavigation: FragmentNavigation?) {
        val tvId = getTvId(currentCategory, itemPosition)
        fragmentNavigation?.startDetailsActivity(tvId, Constants.TV_ENTERTAINMENT)
    }

    private fun getTvShow(currentCategory: EntertainmentCategory, itemPosition: Int): TvBody{
        val tvList = currentCategory.list as ArrayList<TvBody>
        return tvList[itemPosition]
    }

    private fun getTvId(currentCategory: EntertainmentCategory, itemPosition: Int): Int?{
        val tv = getTvShow(currentCategory, itemPosition)
        return tv.entertainmentId
    }

    override fun attachView(view: TvContract.View) {
        this.view = view
    }

    override fun deAttachView() {
        this.view = null
    }
}