package com.example.karem.entertainment.presenters

import com.example.karem.entertainment.contracts.MovieCastContract
import com.example.karem.entertainment.interactors.MovieCastInteractor
import com.example.karem.entertainment.models.CastBody
import com.example.karem.entertainment.utils.Constants

class CastPresenter: MovieCastContract.Presenter, MovieCastContract.Presenter.OnCastCallFinished{

    private var castView: MovieCastContract.View?= null
    private val castInteractor = MovieCastInteractor(this)

    private var castMembers:ArrayList<CastBody>?= null

    override fun loadCastData(entertainmentId: Int?, entertainmentType: String?) {
        when (entertainmentType) {
            Constants.MOVIE_ENTERTAINMENT -> castInteractor.getMovieCastMembers(entertainmentId)
            Constants.TV_ENTERTAINMENT -> castInteractor.getTvCastMembers(entertainmentId)
        }
    }

    override fun onSuccess(castMembers: ArrayList<CastBody>?) {
        initCastMembersArrayList(castMembers)
        initCastRecyclerView()
    }

    private fun initCastMembersArrayList(castMembers: ArrayList<CastBody>?){
        this.castMembers = castMembers
    }

    private fun initCastRecyclerView(){
        castView?.initCastRecyclerView()
    }

    override fun onFailure(throwable: Throwable) {
        castView?.showFailureCause(throwable.cause.toString())
    }

    override fun attachView(view: MovieCastContract.View) {
        castView = view
    }

    override fun deAttachView() {
        castView = null
    }

    override fun onCastMemberViewBind(castMemberView: MovieCastContract.View.CastMemberView, position: Int) {
        castMemberView.setCastMemberCharacter(castMembers?.get(position)?.character)
        castMemberView.setCastMemberName(castMembers?.get(position)?.name)
        castMemberView.setCastMemberImage(getCastMemberImageUrl(castMembers?.get(position)?.profilePath))
    }

    private fun getCastMemberImageUrl(subImageUrl: String?):String{
        return Constants.IMAGE_BASE_URL + subImageUrl
    }

    override fun getCastMembersCount(): Int? {
        return castMembers?.size
    }
}