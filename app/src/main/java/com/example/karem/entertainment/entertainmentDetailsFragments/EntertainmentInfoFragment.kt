package com.example.karem.entertainment.entertainmentDetailsFragments

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.karem.entertainment.R
import com.example.karem.entertainment.activities.SliderActivity
import com.example.karem.entertainment.adapters.CrewRecyclerViewAdapter
import com.example.karem.entertainment.adapters.MovieTrailersRecyclerViewAdapter
import com.example.karem.entertainment.contracts.EntertainmentInfoDetailsContract
import com.example.karem.entertainment.interfaccs.InfoFragmentCommunication
import com.example.karem.entertainment.models.*
import com.example.karem.entertainment.presenters.InfoFragmentPresenter
import com.example.karem.entertainment.utils.Constants
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_info.*
import kotlinx.android.synthetic.main.fragment_info.view.*

class EntertainmentInfoFragment : Fragment(), EntertainmentInfoDetailsContract.View, InfoFragmentCommunication {

    private val movieInfoPresenter = InfoFragmentPresenter()

    private var entertainmentType:String?= null
    private var entertainmentId:Int?= null

    private lateinit var entertainmentImages:EntertainmentImagesResponse
    private var entertainmentTrailers: EntertainmentTrailersResponse?= null
    private lateinit var movieInfo: MovieInfoResponse
    private lateinit var tvInfo: TvInfoResponse

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        setEntertainmentArguments()
        val view=  inflater.inflate(R.layout.fragment_info, container, false)
        view.moviePosterImageView.clipToOutline = true
        view.movieBackdropsImageView.clipToOutline = true
        loadDetailsFragment()
        return view
    }

    private fun loadDetailsFragment() {
        val fragmentManager = childFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()

        if (entertainmentType == Constants.MOVIE_ENTERTAINMENT) {
            val movieDetailsFragment = MovieDetailsFragment()
            movieDetailsFragment.setMovieDetails(movieInfo)
            fragmentTransaction.add(R.id.fragmentDetails, movieDetailsFragment)
            fragmentTransaction.commit()
        }
        else {
            val tvInfoDetailsFragment = TvDetailsFragment()
            tvInfoDetailsFragment.setTvDetails(tvInfo)
            fragmentTransaction.add(R.id.fragmentDetails, tvInfoDetailsFragment)
            fragmentTransaction.commit()
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        movieInfoPresenter.loadMovieData(entertainmentId!!)
        movieInfoPresenter.attachView(this)
        if (entertainmentType == Constants.MOVIE_ENTERTAINMENT){
            attachMovieDetails()
        }else{
            attachTvDetails()
        }
        setAddRateListener()
    }

    override fun setMovieInfo(movieInfo: MovieInfoResponse, entertainmentImages: EntertainmentImagesResponse, entertainmentTrailers: EntertainmentTrailersResponse?) {
        this.movieInfo = movieInfo
        this.entertainmentTrailers = entertainmentTrailers
        this.entertainmentImages = entertainmentImages
    }

    override fun setTvInfo(tvInfo: TvInfoResponse, entertainmentImages: EntertainmentImagesResponse, entertainmentTrailers: EntertainmentTrailersResponse?) {
        this.tvInfo = tvInfo
        this.entertainmentImages = entertainmentImages
        this.entertainmentTrailers = entertainmentTrailers
    }

    private fun setEntertainmentArguments() {
        val arguments = arguments
        arguments?.apply {
            entertainmentId = getInt(Constants.ENTERTAINMENT_ID_BUNDLE_KEY)
            entertainmentType = getString(Constants.ENTERTAINMENT_TYPE_KEY)
        }
    }

    override fun setMovieRate(rate: Float?) {
        movieRateTextView.text = rate.toString()
        circularRatingProgressbar.progress = rate!!.toInt()
    }

    override fun initCrewRecyclerView() {
        initCrewRecyclerViewAdapter()
        initCrewRecyclerViewLayoutManager()
    }

    private fun initCrewRecyclerViewAdapter(){
        val crewRecyclerViewAdapter = CrewRecyclerViewAdapter(movieInfoPresenter)
        movieCrewRecyclerView.adapter = crewRecyclerViewAdapter
    }

    private fun initCrewRecyclerViewLayoutManager(){
        val crewRecyclerViewLayoutManager = GridLayoutManager(context, 2)
        movieCrewRecyclerView.layoutManager = crewRecyclerViewLayoutManager
    }

    override fun initMovieTrailersRecyclerView(){
        initLayoutManager(movieTrailersRecyclerView)
        initMovieTrailerRecyclerViewAdapter(movieTrailersRecyclerView)
    }

    private fun initLayoutManager(recyclerView: RecyclerView){
        val linearLayoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
        recyclerView.layoutManager =  linearLayoutManager
    }

    private fun initMovieTrailerRecyclerViewAdapter(recyclerView: RecyclerView){
        val movieTrailersAdapter = MovieTrailersRecyclerViewAdapter(movieInfoPresenter)
        recyclerView.adapter = movieTrailersAdapter
    }

    override fun setMoviePostersCover(imageUrl: String?) {
        Picasso.get().load(imageUrl).into(moviePosterImageView)
    }

    override fun setMovieBackdropsCover(imageUrl: String?) {
        Picasso.get().load(imageUrl).into(movieBackdropsImageView)
    }

    override fun setMovieOverview(overview: String?) {
        movieInfoDescriptionTextView.text = overview
    }

    override fun setMovieVoteCount(voteCount: Int?) {
        movieVoteCountTextView.text = voteCount.toString()
    }

    override fun openTrailerOnYoutube(trailerUri: Uri) {
        val videoClient = Intent(Intent.ACTION_VIEW).apply { data = trailerUri}
        startActivityForResult(videoClient, 1234)
    }

    override fun openPostersActivity(moviePosters: ArrayList<ImagesBody>?) {
        val intent = Intent(activity, SliderActivity::class.java).apply {
            putExtra(Constants.MOVIE_POSTERS_INTENT_FLAG, moviePosters!!)
            putExtra(Constants.ORIENTATION_INTENT_FLAG, Constants.PORTRAIT_ORIENTATION)
        }
        startActivity(intent)
    }

    override fun openBackdropActivity(movieBackdrops: ArrayList<ImagesBody>?) {
        val intent = Intent(activity, SliderActivity::class.java).apply {
            putExtra(Constants.MOVIE_BACKDROPS_INTENT_FLAG, movieBackdrops)
            putExtra(Constants.ORIENTATION_INTENT_FLAG, Constants.LANDSCAPE_ORIENTATION)
        }
        startActivity(intent)
    }

    private fun attachMovieDetails() {
        movieInfoPresenter.apply {
            setMovieInfo(movieInfo)
            setMovieImages(entertainmentImages)
            setMovieTrailers(entertainmentTrailers)
        }
    }

    private fun attachTvDetails(){
        movieInfoPresenter.apply {
            setTvInfo(tvInfo)
            setMovieImages(entertainmentImages)
            setMovieTrailers(entertainmentTrailers)
        }
    }

    override fun setBackdropImageListener(){
        movieBackdropsImageView.setOnClickListener {
            movieInfoPresenter.handleMovieBackdropCoverClicked()
        }
    }

    override fun setPosterImageListener(){
        moviePosterImageView.setOnClickListener {
            movieInfoPresenter.handleMoviePosterClicked()
        }
    }

    override fun onResume() {
        super.onResume()
        movieInfoPresenter.attachView(this)
    }

    override fun onPause() {
        super.onPause()
        movieInfoPresenter.deAttachView()
    }

    override fun showFailureCause(failureCause: String) {
        Toast.makeText(activity, failureCause, Toast.LENGTH_SHORT).show()
    }

    private fun setAddRateListener(){
        addRateImageView.setOnClickListener {
            Toast.makeText(activity,"this feature will be available soon", Toast.LENGTH_SHORT).show() }
    }
}