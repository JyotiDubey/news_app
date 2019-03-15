package com.grab.news.data.local.database

import androidx.room.*
import com.grab.news.data.model.News
import io.reactivex.Flowable

/**
 * Created by jyotidubey on 2019-03-12.
 */
@Dao
abstract class NewsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insertAll(news: List<News>?)

    @Query("SELECT * FROM news")
    abstract fun loadAll(): Flowable<List<News>>

    @Query("DELETE FROM news")
    abstract fun deleteAll()

    @Transaction
   open fun clearAndInsert(news: List<News>) {
        deleteAll()
        insertAll(news)
    }
}