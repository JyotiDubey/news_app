package com.grab.news.data.remote

import com.grab.news.data.remote.retrofit.NewsService
import com.grab.news.data.remote.retrofit.NewsServiceGenerator
import javax.inject.Inject

/**
 * Created by jyotidubey on 2019-03-09.
 */
class NewsApiHelper @Inject constructor() : ApiHelper {

    private val newsService = NewsServiceGenerator.createService(NewsService::class.java)
    override fun fetchHeadlinesFromServer(country: String, page: Int) = newsService.getTopHeadlines(country, page)
}