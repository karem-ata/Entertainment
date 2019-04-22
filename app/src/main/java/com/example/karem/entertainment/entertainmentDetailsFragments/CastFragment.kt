package com.example.karem.entertainment.entertainmentDetailsFragments

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.karem.entertainment.R
import com.example.karem.entertainment.adapters.CastRecyclerViewAdapter
import com.example.karem.entertainment.contracts.MovieCastContract
import com.example.karem.entertainment.presenters.CastPresenter
import com.example.karem.entertainment.utils.Constants
import kotlinx.android.synthetic.main.fragment_cast.*

class CastFragment : Fragment(), MovieCastContract.View {

    private val castPresenter = CastPresenter()
    private var movieId:Int?= null
    private var entertainmentType: String?= null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setEntertainmentCastArguments()
    }

    private fun setEntertainmentCastArguments(){
        val arguments = arguments
        arguments?.apply {
            movieId = getInt(Constants.ENTERTAINMENT_ID_BUNDLE_KEY)
            entertainmentType = getString(Constants.ENTERTAINMENT_TYPE_KEY)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_cast, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        requestCastData()
    }

    private fun requestCastData(){
        castPresenter.loadCastData(movieId, entertainmentType)
    }

    override fun initCastRecyclerView() {
        initCastRecyclerViewLayoutManager()
        initCastRecyclerViewAdapter()
    }

    private fun initCastRecyclerViewLayoutManager(){
        val castRecyclerViewLayoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
        moviesCastRecyclerView.layoutManager = castRecyclerViewLayoutManager
    }

    private fun initCastRecyclerViewAdapter(){
        val castRecyclerViewAdapter = CastRecyclerViewAdapter(castPresenter)
        moviesCastRecyclerView.adapter = castRecyclerViewAdapter
    }

    override fun onPause() {
        super.onPause()
        castPresenter.deAttachView()
    }

    override fun onResume() {
        super.onResume()
        castPresenter.attachView(this)
    }

    override fun showFailureCause(failureCause: String) {
        Toast.makeText(activity, failureCause, Toast.LENGTH_SHORT).show()
    }
}
