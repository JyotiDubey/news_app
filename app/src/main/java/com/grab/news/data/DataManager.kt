package com.grab.news.data

import com.grab.news.data.model.News
import io.reactivex.Flowable
import io.reactivex.Single


/**
 * Created by jyotidubey on 2019-03-09.
 */

interface DataManager {

    fun loadNewsFromServer(page: Int): Single<Result<Int>>

    fun loadNewsFromRepository(): Flowable<List<News>>

}