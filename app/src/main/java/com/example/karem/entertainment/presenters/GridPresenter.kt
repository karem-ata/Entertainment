package com.example.karem.entertainment.presenters

import android.annotation.SuppressLint
import android.util.Log
import com.example.karem.entertainment.contracts.EntertainmentBaseContract
import com.example.karem.entertainment.interactors.MoviesInteractor
import com.example.karem.entertainment.interactors.TvInteractor
import com.example.karem.entertainment.interfaccs.FragmentNavigation
import com.example.karem.entertainment.models.EntertainmentCategory
import com.example.karem.entertainment.models.MovieBody
import com.example.karem.entertainment.models.TvBody
import com.example.karem.entertainment.utils.Constants
import java.time.LocalDate
import java.time.ZoneId
import java.util.*
import kotlin.collections.ArrayList
import kotlin.math.absoluteValue

class GridPresenter(private val categoryType: String):EntertainmentBasePresenter(), EntertainmentBaseContract.Interactor.OnFinishedListener {

    private var view: EntertainmentBaseContract.View?= null

    override fun loadData(currentCategory: EntertainmentCategory) {
        showLoadingProgressBar(currentCategory, true)
        when (categoryType) {
            Constants.TV_ENTERTAINMENT -> TvInteractor(this).getTvCategoryList(currentCategory)
            Constants.MOVIE_ENTERTAINMENT -> MoviesInteractor(this).getCategoryMoviesList(currentCategory)
        }
    }

    override fun onEntertainmentCallFinished(entertainmentList: ArrayList<*>?, entertainmentCategory: EntertainmentCategory) {
        if (entertainmentList != null) {
            when (categoryType) {
                Constants.MOVIE_ENTERTAINMENT -> {
                    entertainmentList as ArrayList<MovieBody>
                    var updatedList: ArrayList<MovieBody>? = null
                    when (entertainmentCategory.name) {
                        Constants.ENTERTAINMENT_UPCOMING_MOVIES -> {
                            updatedList = updateUpcomingMoviesList(entertainmentList)
                        }
                        Constants.ENTERTAINMENT_NOW_PLAYING_MOVIES -> {
                            updatedList = updateNowPlayingMoviesList(entertainmentList)
                        }
                    }
                    updateCategoryRecyclerView(entertainmentCategory, updatedList
                            ?: entertainmentList)
                }
                Constants.TV_ENTERTAINMENT -> {
                    updateCategoryRecyclerView(entertainmentCategory, entertainmentList)
                }
            }
            when {
                entertainmentCategory.list.size < 10 -> loadData(entertainmentCategory)
                else -> view?.hideProgressBar(entertainmentCategory.progressBarId)
            }
        }
        else{
            hideLoadingProgressBar(entertainmentCategory)
        }
    }

    override fun getItemType(currentCategory: EntertainmentCategory, position: Int): Int {
        return when(currentCategory.list[position]){
            null -> Constants.GRID_PROGRESS_TYPE
            else -> Constants.ENTERTAINMENT_TYPE
        }
    }

    @SuppressLint("SimpleDateFormat")
    private fun updateUpcomingMoviesList(upcomingMovieList: ArrayList<MovieBody>): ArrayList<MovieBody>{
        val updatedUpcomingMovies = ArrayList<MovieBody>()
        for(movie in upcomingMovieList){
            val releaseDateString = movie.releaseDate

            val localDate = LocalDate.parse(releaseDateString)
            val releaseDate = Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant())

            if(((releaseDate.time - Date().time)/1000/60/60/24) >= 6){
                updatedUpcomingMovies.add(movie)
            }
        }
        return updatedUpcomingMovies
    }

    private fun updateNowPlayingMoviesList(upcomingMovieList: ArrayList<MovieBody>): ArrayList<MovieBody>{
        val updatedNowPlayingMovies = ArrayList<MovieBody>()
        for(movie in upcomingMovieList){
            val releaseDateString = movie.releaseDate

            val localDate = LocalDate.parse(releaseDateString)
            val releaseDate = Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant())

            if(((releaseDate.time - Date().time)/(1000*60*60*24)).absoluteValue in 0.. 6){
                updatedNowPlayingMovies.add(movie)
            }
        }
        return updatedNowPlayingMovies
    }

    override fun onBindRowViewAtPosition(currentCategory: EntertainmentCategory, position: Int, rowView: EntertainmentBaseContract.EntertainmentRowView) {
        when (categoryType){
            Constants.MOVIE_ENTERTAINMENT ->{
                val movie = getMovie(currentCategory, position)
                rowView.setEntertainmentPoster(movie.entertainmentPoster)
                rowView.setEntertainmentTitle(movie.entertainmentTitle)
            }
            Constants.TV_ENTERTAINMENT -> {
                val tv = getTvShow(currentCategory, position)
                rowView.setEntertainmentTitle(tv.entertainmentTitle)
                rowView.setEntertainmentPoster(tv.entertainmentPoster)
            }
        }}

    override fun onItemClickHandler(itemPosition: Int, currentCategory: EntertainmentCategory, fragemetNavigation: FragmentNavigation?) {
        when(categoryType) {
            Constants.MOVIE_ENTERTAINMENT -> {
                val movieId = getMovieId(currentCategory, itemPosition)
                fragemetNavigation?.startDetailsActivity(movieId, Constants.MOVIE_ENTERTAINMENT)
            }
            Constants.TV_ENTERTAINMENT -> {
                Log.e("kkk",";;")
                val tvId = getTvId(currentCategory, itemPosition)
                fragemetNavigation?.startDetailsActivity(tvId, Constants.TV_ENTERTAINMENT)
            }
        }
    }

    override fun onFailure(throwable: Throwable) {

    }

    private fun getTvShow(currentCategory: EntertainmentCategory, itemPosition: Int): TvBody{
        val tvList = currentCategory.list as ArrayList<TvBody>
        return tvList[itemPosition]
    }

    private fun getTvId(currentCategory: EntertainmentCategory, itemPosition: Int): Int?{
        val tv = getTvShow(currentCategory, itemPosition)
        return tv.entertainmentId
    }

    private fun getMovie(currentCategory: EntertainmentCategory, itemPosition: Int): MovieBody{
        val movieList = currentCategory.list as ArrayList<MovieBody>
        return movieList[itemPosition]
    }

    private fun getMovieId(currentCategory: EntertainmentCategory, itemPosition: Int):Int?{
        val movie = getMovie(currentCategory, itemPosition)
        return movie.entertainmentId
    }

    fun attachView(view: EntertainmentBaseContract.View){
        this.view = view
    }

    fun deAttachView(){
        view = null
    }
}