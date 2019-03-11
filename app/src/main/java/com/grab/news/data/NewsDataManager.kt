package com.grab.news.data

import com.grab.news.data.remote.NewsApiHelper

/**
 * Created by jyotidubey on 2019-03-09.
 */
class NewsDataManager : DataManager {
    private val apiHelper = NewsApiHelper()
    override fun getTopHeadlines(country: String, page: Int) = apiHelper.getTopHeadlines(country, page)

}