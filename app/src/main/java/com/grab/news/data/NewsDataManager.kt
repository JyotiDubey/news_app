package com.grab.news.data

import com.grab.news.data.local.database.DBHelper
import com.grab.news.data.model.News
import com.grab.news.data.remote.ApiHelper

import io.reactivex.Completable
import io.reactivex.Observable
import javax.inject.Inject


/**
 * Created by jyotidubey on 2019-03-09.
 */
const val INITIAL_PAGE = 1
class NewsDataManager
@Inject constructor(private val dbHelper: DBHelper, private val apiHelper: ApiHelper) :
    DataManager {

    override fun updateRepository(page: Int, news:List<News>):Completable{
        return Observable.just(news)
            .flatMapCompletable {
                if(page==INITIAL_PAGE){
                    dbHelper.invalidateAndInsertIntoRepository(news.filter { it.url != null })
                }else{
                    dbHelper.insertIntoRepository(news.filter { it.url != null })
                }
            }
    }

    override fun loadNewsFromServer(page: Int) = apiHelper.loadNews(page)

    override fun loadNewsFromRepository() = dbHelper.getNewsFromRepository()


}