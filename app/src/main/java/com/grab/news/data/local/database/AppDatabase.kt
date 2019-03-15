package com.grab.news.data.local.database


import androidx.room.Database
import androidx.room.RoomDatabase
import com.grab.news.data.model.News

/**
 * Created by jyotidubey on 2019-03-12.
 */
@Database(entities = [(News::class)], version = 2)
abstract class AppDatabase : RoomDatabase() {

    abstract fun newsDao(): NewsDao
}