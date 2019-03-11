package com.grab.news.data.remote

import com.grab.news.data.remote.retrofit.NewsService
import com.grab.news.data.remote.retrofit.NewsServiceGenerator

/**
 * Created by jyotidubey on 2019-03-09.
 */
class NewsApiHelper : ApiHelper {

    private val newsService = NewsServiceGenerator.createService(NewsService::class.java)
    override fun getTopHeadlines(country: String, page: Int) = newsService.getTopHeadlines(country, page)
}