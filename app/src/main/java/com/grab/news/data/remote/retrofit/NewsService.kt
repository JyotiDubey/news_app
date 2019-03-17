package com.grab.news.data.remote.retrofit

import com.grab.news.BuildConfig
import com.grab.news.data.model.NewsListResponse
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Created by jyotidubey on 2019-03-09.
 */
interface NewsService{
    @GET("/v2/top-headlines")
    fun loadNews(
        @Query("page") page: Int,
        @Query("country") country: String = "us",
        @Query("apiKey") api_key: String = BuildConfig.API_KEY
    ): Single<NewsListResponse>

}