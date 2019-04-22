package com.example.karem.entertainment.adapters

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.karem.entertainment.R
import com.example.karem.entertainment.contracts.MovieCastContract
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.cast_recyclerview_item.view.*

class CastRecyclerViewAdapter(private val castPresenter: MovieCastContract.Presenter): RecyclerView.Adapter<CastRecyclerViewAdapter.CastMemberViewHolder>() {

    override fun getItemCount(): Int {
        return castPresenter.getCastMembersCount() ?: 0
    }

    override fun onCreateViewHolder(parent: ViewGroup, position: Int): CastMemberViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.cast_recyclerview_item, parent, false)
        return CastMemberViewHolder(view)
    }

    override fun onBindViewHolder(castViewHolder: CastMemberViewHolder, position: Int) {
        castPresenter.onCastMemberViewBind(castViewHolder, position)
    }

    inner class CastMemberViewHolder(private val view: View): RecyclerView.ViewHolder(view), MovieCastContract.View.CastMemberView{
        override fun setCastMemberImage(imageUrl: String?) {
            Picasso.get().load(imageUrl).into(view.castMemberImageView)
        }

        override fun setCastMemberName(name: String?) {
            view.castMemberNameTextView.text = name
        }

        override fun setCastMemberCharacter(name: String?) {
            view.castMemberCharacterTextView.text = name
        }
    }
}