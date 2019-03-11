package com.grab.news.data

import com.grab.news.data.model.NewsListResponse
import io.reactivex.Single

/**
 * Created by jyotidubey on 2019-03-09.
 */
interface DataManager{
    fun getTopHeadlines(country: String, page: Int): Single<NewsListResponse>

}