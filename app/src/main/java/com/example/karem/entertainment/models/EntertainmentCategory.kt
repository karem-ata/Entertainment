package com.example.karem.entertainment.models

import android.support.v7.widget.RecyclerView

class EntertainmentCategory(categoryName: String, val recyclerView: RecyclerView, val progressBarId: Int, movieId:Int?= null, var loading:Boolean= true) {
    val name = categoryName
    var pageNo = 1
    var list: ArrayList<Any?> = ArrayList()
    var recyclerViewPreviousTotalItems = 0
    var id = movieId
}