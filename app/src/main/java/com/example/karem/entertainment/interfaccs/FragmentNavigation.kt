package com.example.karem.entertainment.interfaccs

interface FragmentNavigation {
    fun navigateToAllItemsFragment(entertainmentCategoryName: String, entertainmentCategoryType: String){}
    fun startDetailsActivity(entertainmentId: Int?, entertainmentType: String)
}