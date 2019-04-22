package com.example.karem.entertainment.adapters

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import com.example.karem.entertainment.EntertainmentHomeFragments.BaseGridFragment
import com.example.karem.entertainment.models.EntertainmentImagesResponse
import com.example.karem.entertainment.models.EntertainmentTrailersResponse
import com.example.karem.entertainment.models.MovieInfoResponse
import com.example.karem.entertainment.entertainmentDetailsFragments.CastFragment
import com.example.karem.entertainment.entertainmentDetailsFragments.EntertainmentInfoFragment
import com.example.karem.entertainment.interfaccs.DetailsFragmentCommunication
import com.example.karem.entertainment.interfaccs.FragmentNavigation
import com.example.karem.entertainment.models.TvInfoResponse
import com.example.karem.entertainment.utils.Constants

class FragmentViewPagerAdapter(fragmentManager: FragmentManager, entertainmentId: Int, private val movieInfo: MovieInfoResponse?, private val tvInfo: TvInfoResponse?, private val entertainmentImages: EntertainmentImagesResponse, private val entertainmentTrailers: EntertainmentTrailersResponse?, private val fragmentNavigation: FragmentNavigation): FragmentPagerAdapter(fragmentManager) {

    private var entertainmentInfoBundle: Bundle = Bundle()
    private var entertainmentCastBundle: Bundle = Bundle()
    private var similarEntertainmentBundle = Bundle()

    init {
        entertainmentInfoBundle.apply {
            putInt(Constants.ENTERTAINMENT_ID_BUNDLE_KEY, entertainmentId)
            if (movieInfo!= null)
                putString(Constants.ENTERTAINMENT_TYPE_KEY, Constants.MOVIE_ENTERTAINMENT)
            else
                putString(Constants.ENTERTAINMENT_TYPE_KEY, Constants.TV_ENTERTAINMENT)
        }

        entertainmentCastBundle.apply {
            if (movieInfo!= null) {
                putString(Constants.ENTERTAINMENT_TYPE_KEY, Constants.MOVIE_ENTERTAINMENT)
            }else {
                putString(Constants.ENTERTAINMENT_TYPE_KEY, Constants.TV_ENTERTAINMENT)
            }
            putInt(Constants.ENTERTAINMENT_ID_BUNDLE_KEY, entertainmentId) }

        similarEntertainmentBundle.apply {
            putInt(Constants.ENTERTAINMENT_ID_BUNDLE_KEY, entertainmentId)
            if (movieInfo != null) {
                putString(Constants.CATEGORY_NAME_TAG, Constants.ENTERTAINMENT_SIMILAR_MOVIES)
                putString(Constants.CATEGORY_TYPE_TAG, Constants.MOVIE_ENTERTAINMENT)
            }else{
                putString(Constants.CATEGORY_NAME_TAG, Constants.ENTERTAINMENT_SIMILAR_TV)
                putString(Constants.CATEGORY_TYPE_TAG, Constants.TV_ENTERTAINMENT)
            }
        }
    }

    override fun getItem(position: Int): Fragment? {
        return when(position){
            0 -> {
                val entertainmentInfoFragment = EntertainmentInfoFragment()
                if (movieInfo!= null) {
                    entertainmentInfoFragment.setMovieInfo(movieInfo, entertainmentImages, entertainmentTrailers)
                }else if (tvInfo!= null){
                    entertainmentInfoFragment.setTvInfo(tvInfo, entertainmentImages, entertainmentTrailers)
                }
                entertainmentInfoFragment.arguments = entertainmentInfoBundle
                entertainmentInfoFragment
            }

            1 -> {
                val castFragment = CastFragment()
                castFragment.arguments = entertainmentCastBundle
                castFragment
            }

            2 -> {
                val similarMoviesFragment = BaseGridFragment()
                similarMoviesFragment.attachFragmentNavigation(fragmentNavigation)
                similarMoviesFragment.arguments = similarEntertainmentBundle
                similarMoviesFragment
            }
            else -> null
        }
    }

    override fun getCount(): Int {
        return 3
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return when(position){
            0 -> "Info"
            1 -> "Cast"
            2 -> "Similar"
            else -> ""
        }
    }
}