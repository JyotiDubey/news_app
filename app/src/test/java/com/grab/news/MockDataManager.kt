package com.grab.news

import com.grab.news.data.DataManager
import com.grab.news.data.model.News
import com.grab.news.data.model.NewsListResponse
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Single

/**
 * Created by jyotidubey on 2019-03-15.
 */
class MockDataManager : DataManager{
    override fun loadNewsFromServer(page: Int): Single<NewsListResponse> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun loadNewsFromRepository(): Flowable<List<News>> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun insertIntoRepository(news: List<News>): Completable {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun invalidateAndInsertIntoRepository(news: List<News>): Completable {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}