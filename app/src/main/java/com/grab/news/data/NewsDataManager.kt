package com.grab.news.data

import com.grab.news.data.local.database.DBHelper
import com.grab.news.data.model.News
import com.grab.news.data.remote.ApiHelper
import io.reactivex.Flowable
import io.reactivex.Single
import javax.inject.Inject

/**
 * Created by jyotidubey on 2019-03-09.
 */
class NewsDataManager @Inject constructor(private val dbHelper: DBHelper, private val apiHelper: ApiHelper) : DataManager {

    override fun getNews(country: String, page: Int): Flowable<List<News>>{
       val local =  fetchHeadlinesFromDB()
        val remote = fetchHeadLinesFromApi(country, page).flatMapCompletable { updateLocal(it) }.onErrorComplete()
         return local.mergeWith(remote)
    }

    private fun fetchHeadlinesFromDB() = dbHelper.getNews()

    private fun fetchHeadLinesFromApi(country: String, page: Int) = apiHelper.getTopHeadlines(country,page)
            .flatMap { Single.just(it.news) }

    private fun updateLocal(news: List<News>) = dbHelper.saveLatestNews(news)

}