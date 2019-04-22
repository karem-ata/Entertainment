package com.example.karem.entertainment.adapters

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.karem.entertainment.R
import com.example.karem.entertainment.contracts.EntertainmentInfoDetailsContract
import com.example.karem.entertainment.presenters.InfoFragmentPresenter
import kotlinx.android.synthetic.main.crew_recycler_view_item.view.*

class CrewRecyclerViewAdapter(private val movieInfoPresenter: InfoFragmentPresenter): RecyclerView.Adapter<CrewRecyclerViewAdapter.CrewViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, position: Int): CrewViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.crew_recycler_view_item,parent,false)
        return CrewViewHolder(view)
    }

    override fun getItemCount(): Int { return movieInfoPresenter.getMovieCrewMembersCount() }

    override fun onBindViewHolder(crewViewHolder: CrewViewHolder, position: Int) {
        movieInfoPresenter.onBindCrew(crewViewHolder, position)
    }

    inner class CrewViewHolder(private val view: View): RecyclerView.ViewHolder(view), EntertainmentInfoDetailsContract.CrewRowView{

        override fun setCrewMemberJobTitle(crewMemberJobTitle: String?) {
            view.crewMemberJobTitleTextView.text = crewMemberJobTitle
        }

        override fun setCrewMemberName(crewMemberName: String?) {
            view.crewMemberNameTextView.text = crewMemberName
        }
    }
}