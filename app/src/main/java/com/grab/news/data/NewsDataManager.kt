package com.grab.news.data

import com.grab.news.data.local.database.DBHelper
import com.grab.news.data.model.News
import com.grab.news.data.remote.ApiHelper
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

/**
 * Created by jyotidubey on 2019-03-09.
 */
class NewsDataManager @Inject constructor(private val dbHelper: DBHelper, private val apiHelper: ApiHelper) :
    DataManager {

    override fun loadNewsFromServer(page: Int) = apiHelper.loadNews(page)
        .filter { it.news.isNotEmpty() }
        .flatMapCompletable {
            if (page == 1) {
                invalidateAndInsertIntoRepository(it.news)
            } else {
                insertIntoRepository(it.news)
            }
        }

    override fun loadNewsFromRepository() = dbHelper.getNewsFromRepository()

    override fun insertIntoRepository(news: List<News>) = dbHelper.insertIntoRepository(news)

    override fun invalidateAndInsertIntoRepository(news: List<News>) = dbHelper.invalidateAndInsertIntoRepository(news)

}