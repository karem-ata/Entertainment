package com.example.karem.entertainment.entertainmentDetailsFragments

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.karem.entertainment.R
import com.example.karem.entertainment.interfaccs.DetailsFragmentCommunication
import com.example.karem.entertainment.models.TvInfoResponse
import kotlinx.android.synthetic.main.fragment_tv_details.*
import java.lang.StringBuilder


class TvDetailsFragment : Fragment(), DetailsFragmentCommunication {

    private lateinit var tvDetails: TvInfoResponse

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setTVView()
    }

    private fun setTVView() {
        tvOriginalNameTextView.text = tvDetails.originalTitle
        movieOriginalLanguageTextView.text = tvDetails.originalLanguage
        movieFirstAirDateTextView.text = tvDetails.firstAirDate
        movieLastAirDateTextView.text = tvDetails.lasttAirDate
        tvStatusTextView.text = tvDetails.status
        tvSeasonNoTextView.text = tvDetails.seasonsNo
        val productionCompanies = tvDetails.productionCompanies
        val producionCompaniesText = StringBuilder()
        if (productionCompanies != null) {
            for (productionCompany in productionCompanies){
                producionCompaniesText.append(productionCompany.name + ", ")
            }
            producionCompaniesText.removeSuffix(", ")
        }
        tvProductionCompaniesTextView.text = producionCompaniesText }

    override fun setTvDetails(tvDetails: TvInfoResponse) {
      this.tvDetails = tvDetails
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_tv_details, container, false)
    }

}
