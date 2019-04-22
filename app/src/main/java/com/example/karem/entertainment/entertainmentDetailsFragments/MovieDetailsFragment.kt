package com.example.karem.entertainment.entertainmentDetailsFragments

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.karem.entertainment.R
import com.example.karem.entertainment.interfaccs.DetailsFragmentCommunication
import com.example.karem.entertainment.models.MovieInfoResponse
import kotlinx.android.synthetic.main.fragment_movies_details.*
import java.lang.StringBuilder


class MovieDetailsFragment : Fragment(), DetailsFragmentCommunication {

    private lateinit var movieDetails: MovieInfoResponse

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setMovieDetailsView()

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    private fun setMovieDetailsView() {

        movieOriginalNameTextView.text = movieDetails.originalTitle
        movieOriginalLanguageTextView.text = movieDetails.originalLanguage
        movieReleaseDateTextView.text = movieDetails.releaseDate
        movieRunTimeTextView.text = movieDetails.runtime
        movieBudgetTextView.text = movieDetails.budget
        movieRevenueTextView.text = movieDetails.revenue
        movieTaglineTextView.text = movieDetails.tagLine
        val productionCompanies = movieDetails.productionCompanies
        val producionCompaniesText = StringBuilder()
        if (productionCompanies != null) {
            for (productionCompany in productionCompanies){
                producionCompaniesText.append(productionCompany.name + ", ")
            }
            producionCompaniesText.removeSuffix(", ")
        }
        movieProductionCompaniesTextView.text = producionCompaniesText

    }

    override fun setMovieDetails(movieDetails: MovieInfoResponse) {
        this.movieDetails = movieDetails
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_movies_details, container, false)
    }
}