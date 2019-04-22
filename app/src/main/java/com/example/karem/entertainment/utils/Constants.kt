package com.example.karem.entertainment.utils

object Constants {

    const val API_KEY: String = "686836e815d1818e9e27f0dcfb31cdce"
    const val BASE_URL: String = "https://api.themoviedb.org/3/"
    const val API_VERSION: Int = 3

    const val IMAGE_BASE_URL: String = "https://image.tmdb.org/t/p/w500"

    const val ENTERTAINMENT_NOW_PLAYING_MOVIES = "nowPlayingMovie"
    const val ENTERTAINMENT_UPCOMING_MOVIES = "upcomingMovies"
    const val ENTERTAINMENT_TOP_RATED_MOVIES = "topRated"
    const val ENTERTAINMENT_POPULAR_MOVIES = "popular"
    const val ENTERTAINMENT_SIMILAR_MOVIES = "similarMovies"
    const val ENTERTAINMENT_ON_AIR_TV = "onAir"
    const val ENTERTAINMENT_POPULAR_TV = "popularTv"
    const val ENTERTAINMENT_TOP_RATED_TV = "topRatedTv"
    const val ENTERTAINMENT_TRENDING_TV = "trendingTv"
    const val ENTERTAINMENT_SIMILAR_TV = "similarTv"

    const val MOVIE_ENTERTAINMENT = "movie"
    const val TV_ENTERTAINMENT = "tv"
    const val ENTERTAINMENT_TYPE_KEY = "entertainmentType"

    const val ENTERTAINMENT_ID_BUNDLE_KEY = "movieId"

    const val CATEGORY_NAME_TAG = "categoryName"
    const val CATEGORY_TYPE_TAG = "categoryType"

    const val MOVIE_FRAGMENT_TAG = "movieFragment"
    const val TV_FRAGMENT_TAG = "tvFragment"
    const val SEARCH_FRAGMENT_TAG = "searchFragment"
    const val ALL_ITEMS_FRAGMENT_TAG = "allItemsFragment"

    const val LINEAR_PROGRESS_TYPE = 0
    const val GRID_PROGRESS_TYPE = 1
    const val ENTERTAINMENT_TYPE = 2

    const val MOVIE_ID_INTENT_FLAG = "movieID"
    const val MOVIE_TYPE_INTENT_FLAG = "movieType"
    const val MOVIE_POSTERS_INTENT_FLAG = "moviePosters"
    const val MOVIE_BACKDROPS_INTENT_FLAG = "movieBackdrops"

    const val threshold = 5

    const val IMAGE_THUMBNAIL_BASE_URL = "https://img.youtube.com/vi/"
    const val IMAGE_THUMBNAIL_ENDPOINT_URL = "/hqdefault.jpg"
    const val YOUTUBE_VIDEO_BASE_URL = "http://m.youtube.com/watch?v="

    const val ORIENTATION_INTENT_FLAG = "orientation"
    const val LANDSCAPE_ORIENTATION = "landscape"
    const val PORTRAIT_ORIENTATION = "orientation"
}