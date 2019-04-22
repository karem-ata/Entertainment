package com.example.karem.entertainment.activities

import android.app.ActionBar
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.content.ContextCompat
import android.support.v7.widget.SearchView
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.EditText
import android.widget.Toast
import com.example.karem.entertainment.EntertainmentHomeFragments.BaseGridFragment
import com.example.karem.entertainment.EntertainmentHomeFragments.MoviesFragment
import com.example.karem.entertainment.EntertainmentHomeFragments.TvFragment
import com.example.karem.entertainment.R
import com.example.karem.entertainment.contracts.EntertainmentActivityContract
import com.example.karem.entertainment.presenters.EntertainmentActivityPresenter
import com.example.karem.entertainment.utils.Constants
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.app_bar_layout.*





class EntertainmentActivity : AppCompatActivity(), EntertainmentActivityContract.View, FragmentManager.OnBackStackChangedListener {

    private var activityPresenter: EntertainmentActivityPresenter? = null
    private var searchView: SearchView? = null
    private lateinit var searchItem: MenuItem

    private var isFirstFragment = true
    private lateinit var firstFragment: Fragment
    private lateinit var activeFragment: Fragment

    private val fragmentManager = supportFragmentManager


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        fragmentManager.addOnBackStackChangedListener(this)
        setBottomNavigationClickListener()
        activityPresenter = EntertainmentActivityPresenter(this)
        activityPresenter?.handleBottomNavigationView(R.id.moviesNavigationbar)
    }

    private fun setBottomNavigationClickListener() {
        bottomNavigationView.setOnNavigationItemSelectedListener { item ->
            activityPresenter?.handleBottomNavigationView(item.itemId)
            true
        }
    }

    override fun setToolBarTitle(title: String) {
        supportActionBar?.title = title
    }

    override fun loadFragment(fragment: Fragment, fragmentTag: String) {

        val fragmentTransaction = fragmentManager.beginTransaction()

        var currentFragment = fragmentManager.findFragmentByTag(fragmentTag)

        var entertainmentFragment: Fragment? = null

        when (fragment) {
            is MoviesFragment -> {
                entertainmentFragment = fragment
                entertainmentFragment.attachFragmentEffect(activityPresenter!!)
                entertainmentFragment.attachNavigationInterface(activityPresenter!!)
            }
            is TvFragment -> {
                entertainmentFragment = fragment
                entertainmentFragment.attachFragmentEffect(activityPresenter!!)
                entertainmentFragment.attachNavigationInterface(activityPresenter!!)
            }
            is BaseGridFragment -> fragment.attachFragmentEffect(activityPresenter!!)
        }

        if(currentFragment == null){
            if (isFirstFragment){
                firstFragment = entertainmentFragment ?: fragment
                fragmentTransaction.add(R.id.fragmentContainer, entertainmentFragment
                        ?: fragment, fragmentTag)
                        .addToBackStack(fragmentTag)
                        .commit()
                activeFragment = entertainmentFragment ?: fragment
                isFirstFragment = false
            }else{
                fragmentTransaction.hide(activeFragment).add(R.id.fragmentContainer, entertainmentFragment
                        ?: fragment, fragmentTag)
                        .addToBackStack(fragmentTag)
                        .commit()
                activeFragment = entertainmentFragment ?: fragment
            }
        }else{
            fragmentTransaction
                    .hide(activeFragment)
                    .show(currentFragment)
                    .addToBackStack(fragmentTag)
                    .commit()
            activeFragment = currentFragment
        }

    }


    override fun hideNavigationView() {
        bottomNavigationView.visibility = View.GONE
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.search_menu, menu)

        searchItem = menu.findItem(R.id.action_search)
        searchView = searchItem.actionView as SearchView

        initSearchPlate()

        searchView?.setOnQueryTextListener(object : SearchView.OnQueryTextListener {

            override fun onQueryTextChange(newText: String?): Boolean {
                return true
            }

            override fun onQueryTextSubmit(query: String): Boolean {
                activityPresenter?.handleSearchViewClicked(query)
                return true
            }
        })
        return super.onCreateOptionsMenu(menu)
    }

    private fun initSearchPlate() {
        val searchPlate = searchView?.findViewById(R.id.search_src_text) as EditText
        searchPlate.hint = "Search"
        val searchPlateView: View? = searchView?.findViewById(R.id.search_plate)
        searchPlateView?.setBackgroundColor(ContextCompat.getColor(this, android.R.color.transparent))
    }

    override fun startDetailsActivity(movieId: Int?, entertainmentType: String) {
        val intent = Intent(this, MovieDetailsActivity::class.java).apply {
            putExtra(Constants.MOVIE_ID_INTENT_FLAG, movieId)
            putExtra(Constants.ENTERTAINMENT_TYPE_KEY, entertainmentType)
        }
        startActivity(intent)
    }

    override fun handleNavigationItemColor(index: Int) {
        bottomNavigationView.menu.getItem(index).isChecked = true
    }

    override fun onBackPressed() {
        if (fragmentManager.backStackEntryCount > 1) {
            if (fragmentManager.backStackEntryCount == 2) {
                fragmentManager.beginTransaction().show(firstFragment).commit()
            }
            fragmentManager.popBackStack()

            val searchFragment = fragmentManager.findFragmentByTag(Constants.SEARCH_FRAGMENT_TAG)
            val allItemsFragment = fragmentManager.findFragmentByTag(Constants.ALL_ITEMS_FRAGMENT_TAG)
            if ((searchFragment != null && searchFragment.isVisible) || (allItemsFragment != null && allItemsFragment.isVisible))
                bottomNavigationView.visibility = View.VISIBLE
        } else if (fragmentManager.backStackEntryCount == 1) {
            finish()
        }
    }

    override fun onBackStackChanged() {
        val searchFragment = fragmentManager.findFragmentByTag(Constants.SEARCH_FRAGMENT_TAG)
        if (searchFragment != null && searchFragment.isVisible) {
            activityPresenter?.handleFragmentEffect(Constants.SEARCH_FRAGMENT_TAG)
        }

        val allItemsFragment = fragmentManager.findFragmentByTag(Constants.ALL_ITEMS_FRAGMENT_TAG)
        if (allItemsFragment != null && allItemsFragment.isVisible) {
            activityPresenter?.handleFragmentEffect(Constants.ALL_ITEMS_FRAGMENT_TAG)
        }

        val moviesFragment = fragmentManager.findFragmentByTag(Constants.MOVIE_FRAGMENT_TAG)
        if (moviesFragment != null && moviesFragment.isVisible){
            activityPresenter?.handleFragmentEffect(Constants.MOVIE_FRAGMENT_TAG)
        }

        val tvFragment = fragmentManager.findFragmentByTag(Constants.TV_FRAGMENT_TAG)
        if (tvFragment!= null && tvFragment.isVisible){
            activityPresenter?.handleFragmentEffect(Constants.TV_FRAGMENT_TAG)
        }    }
}