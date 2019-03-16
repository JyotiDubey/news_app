package com.grab.news.data

import com.grab.news.data.local.database.DBHelper
import com.grab.news.data.model.News
import com.grab.news.data.remote.ApiHelper
import com.grab.news.exception.PageLimitExceeded
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.exceptions.Exceptions
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject


/**
 * Created by jyotidubey on 2019-03-09.
 */
const val INITIAL_PAGE = 1
class NewsDataManager
@Inject constructor(private val dbHelper: DBHelper, private val apiHelper: ApiHelper) :
    DataManager {

    override fun loadNewsFromServer(page: Int) = apiHelper.loadNews(page)
        .map { it.news }
        .map { news ->
            if (news.isEmpty()) {
                throw Exceptions.propagate(PageLimitExceeded())
            } else {
                news
            }
        }.flatMapCompletable { news ->
            return@flatMapCompletable updateRepository(page, news)
        }.toSingle { 1 }
        .map { Result.success(1) }
        .onErrorReturn { Result.failure(it) }
        .subscribeOn(Schedulers.io())

    override fun loadNewsFromRepository() = dbHelper.getNewsFromRepository()

    private fun updateRepository(page: Int, news:List<News>):Completable{
        return Observable.just(news)
            .flatMapCompletable {
                if(page==INITIAL_PAGE){
                    dbHelper.invalidateAndInsertIntoRepository(news.filter { it.url != null })
                }else{
                    dbHelper.insertIntoRepository(news.filter { it.url != null })
                }
            }
    }




}