package com.grab.news.di.module

import android.content.Context
import androidx.room.Room
import com.grab.news.APP_DATABASE_NAME
import com.grab.news.data.DataManager
import com.grab.news.data.NewsDataManager
import com.grab.news.data.local.database.AppDatabase
import com.grab.news.data.local.database.DBHelper
import com.grab.news.data.local.database.NewsDBHelper
import com.grab.news.data.local.database.NewsDao
import com.grab.news.data.remote.ApiHelper
import com.grab.news.data.remote.NewsApiHelper
import com.grab.news.di.DatabaseInfo
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * Created by jyotidubey on 2019-03-17.
 */
@Module
class RepositoryModule{

    @Singleton
    @Provides
    internal fun provideDataManager(manager: NewsDataManager): DataManager = manager

    @Singleton
    @Provides
    internal fun provideApiHelper(apiHelper: NewsApiHelper): ApiHelper = apiHelper

    @Provides
    @DatabaseInfo
    internal fun provideDatabaseName() = APP_DATABASE_NAME

    @Singleton
    @Provides
    internal fun provideAppDatabase(context: Context, @DatabaseInfo name: String): AppDatabase {
        return Room.databaseBuilder(context, AppDatabase::class.java, name)
            .fallbackToDestructiveMigration()
            .build()
    }

    @Singleton
    @Provides
    internal fun provideDBHelper(dbHelper: NewsDBHelper): DBHelper = dbHelper

    @Singleton
    @Provides
    internal fun provideNewsDao(database: AppDatabase): NewsDao = database.newsDao()
}