package com.grab.news.data.remote

import com.grab.news.data.remote.retrofit.NewsService
import javax.inject.Inject

/**
 * Created by jyotidubey on 2019-03-09.
 */
class NewsApiHelper @Inject constructor(private val newsService: NewsService) : ApiHelper {

    override fun loadNews(page: Int) = newsService.loadNews(page)
}