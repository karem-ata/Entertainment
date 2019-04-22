package com.example.karem.entertainment.contracts

import com.example.karem.entertainment.models.CastBody

interface MovieCastContract {

    interface View {
        fun initCastRecyclerView()
        fun showFailureCause(failureCause: String)

        interface CastMemberView {
            fun setCastMemberImage(imageUrl: String?)
            fun setCastMemberName(name: String?)
            fun setCastMemberCharacter(name: String?)
        }
    }

    interface Presenter{
        fun loadCastData(entertainmentId: Int?, entertainmentType: String?)
        fun onCastMemberViewBind(castMemberView: View.CastMemberView, position: Int)
        fun getCastMembersCount():Int?
        fun attachView(view: View)
        fun deAttachView()

        interface OnCastCallFinished{
            fun onSuccess(castMembers: ArrayList<CastBody>?)
            fun onFailure(throwable: Throwable)
        }
    }

    interface Interactor{
        fun getMovieCastMembers(movieId: Int?)
        fun getTvCastMembers(tvId: Int?)
    }
}