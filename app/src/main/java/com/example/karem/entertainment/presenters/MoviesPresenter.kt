package com.example.karem.entertainment.presenters

import com.example.karem.entertainment.contracts.EntertainmentBaseContract
import com.example.karem.entertainment.contracts.MoviesContract
import com.example.karem.entertainment.interactors.MoviesInteractor
import com.example.karem.entertainment.interfaccs.FragmentEffect
import com.example.karem.entertainment.interfaccs.FragmentNavigation
import com.example.karem.entertainment.models.EntertainmentCategory
import com.example.karem.entertainment.models.MovieBody
import com.example.karem.entertainment.utils.Constants
import java.time.LocalDate
import java.time.ZoneId
import java.util.*
import kotlin.collections.ArrayList
import kotlin.math.absoluteValue

class MoviesPresenter: EntertainmentBasePresenter(),
        MoviesContract.Presenter,EntertainmentBaseContract.Interactor.OnFinishedListener {

    private var view: MoviesContract.View?= null
    private var moviesInteractor: MoviesInteractor = MoviesInteractor(this)

    override fun loadData(currentCategory: EntertainmentCategory) {
        currentCategory.loading = false
        showLoadingProgressBar(currentCategory)
        moviesInteractor.getCategoryMoviesList(currentCategory)
    }

    override fun setCurrentFragmentProperties(fragmentEffect: FragmentEffect?, fragmentTag: String?) {
        fragmentEffect?.handleFragmentEffect(fragmentTag)
    }

    override fun onEntertainmentCallFinished(entertainmentList: ArrayList<*>?, entertainmentCategory: EntertainmentCategory) {
        entertainmentList as ArrayList<MovieBody>?
        entertainmentCategory.loading = true

        if (entertainmentList != null) {
            var updatedList:ArrayList<MovieBody>?= null
            when (entertainmentCategory.name) {
                Constants.ENTERTAINMENT_UPCOMING_MOVIES -> {
                    updatedList = updateUpcomingMoviesList(entertainmentList)
                }
                Constants.ENTERTAINMENT_NOW_PLAYING_MOVIES -> {
                    updatedList = updateNowPlayingMoviesList(entertainmentList)
                }
            }
            updateCategoryRecyclerView(entertainmentCategory, updatedList ?: entertainmentList)
            when {
                //(updatedList != null && updatedList.size <= 1) -> loadData(entertainmentCategory)
                (entertainmentList.size < 1) -> loadData(entertainmentCategory)
                (entertainmentCategory.list.size < 4) -> loadData(entertainmentCategory)
                else -> view?.hideProgressBar(entertainmentCategory.progressBarId)
            }
        }
    }

    override fun onFailure(throwable: Throwable) {
        view?.showFailureCause(throwable.cause.toString())
    }

    private fun updateUpcomingMoviesList(upcomingMovieList: ArrayList<MovieBody>): ArrayList<MovieBody>{
        val updatedUpcomingMoviesList = ArrayList<MovieBody>()
        for(movie in upcomingMovieList){
            val movieDate = getMovieDate(movie)

            if(isMovieDateInTheNextWeek(movieDate)) {updatedUpcomingMoviesList.add(movie)}
        }
        return updatedUpcomingMoviesList
    }

    private fun isMovieDateInTheNextWeek(movieDate: Long): Boolean{
        return ((movieDate - Date().time)/1000/60/60/24) >= 6
    }

    private fun updateNowPlayingMoviesList(upcomingMovieList: ArrayList<MovieBody>): ArrayList<MovieBody>{
        val updatedNowPlayingMoviesList = ArrayList<MovieBody>()

        for(movie in upcomingMovieList){
            val movieDate = getMovieDate(movie)

            if(isMovieDateWithWeekFromNow(movieDate)){updatedNowPlayingMoviesList.add(movie)}
        }
        return updatedNowPlayingMoviesList
    }

    private fun getMovieDate(movie: MovieBody): Long{
        val releaseDateString = movie.releaseDate
        val localDate = LocalDate.parse(releaseDateString)
        val releaseDate = Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant())
        return releaseDate.time
    }

    private fun isMovieDateWithWeekFromNow(movieDate: Long): Boolean {
        return (((movieDate - Date().time) / (1000 * 60 * 60 * 24)).absoluteValue in 0..6)
    }

    override fun onBindRowViewAtPosition(currentCategory: EntertainmentCategory, position: Int, rowView: EntertainmentBaseContract.EntertainmentRowView) {
        val movie = getMovie(currentCategory, position)
        rowView.setEntertainmentTitle(movie.entertainmentTitle)
        rowView.setEntertainmentPoster(movie.entertainmentPoster)
    }

    override fun attachView(view: MoviesContract.View) {
        this.view = view
    }

    override fun deAttachView() {
        this.view = null
    }

    override fun showAllItemsFragment(fragmentNavigation: FragmentNavigation, moviesCategoryName: String) {
        fragmentNavigation.navigateToAllItemsFragment(moviesCategoryName, Constants.MOVIE_ENTERTAINMENT)
    }

    override fun onItemClickHandler(itemPosition: Int, currentCategory: EntertainmentCategory, fragmentFragmentNavigation: FragmentNavigation?) {
        val movieId = getMovieId(currentCategory, itemPosition)
        fragmentFragmentNavigation?.startDetailsActivity(movieId, Constants.MOVIE_ENTERTAINMENT)
    }

    private fun getMovie(currentCategory: EntertainmentCategory, itemPosition: Int): MovieBody{
        val movieList = currentCategory.list as ArrayList<MovieBody>
        return movieList[itemPosition]
    }

    private fun getMovieId(currentCategory: EntertainmentCategory, itemPosition: Int):Int?{
        val movie = getMovie(currentCategory, itemPosition)
        return movie.entertainmentId
    }
}