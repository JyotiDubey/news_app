package com.grab.news.di.module

import android.content.Context
import androidx.recyclerview.widget.LinearLayoutManager
import com.grab.news.data.imageloader.ImageLoader
import com.grab.news.di.scope.ActivityScope
import com.grab.news.ui.news.NewsListAdapter
import com.grab.news.ui.news.viewmodel.NewsListViewModel
import dagger.Module
import dagger.Provides

/**
 * Created by jyotidubey on 2019-03-13.
 */
@Module
class NewsListModule(private val handler: NewsListViewModel.NewsListScreenActionHandler, private var activityContext:Context){


    @ActivityScope
    @Provides
    internal fun provideNewsListAdapter(imageLoader: ImageLoader) = NewsListAdapter(imageLoader,handler)

    @ActivityScope
    @Provides
    internal fun provideLinearLayoutManager() = LinearLayoutManager(activityContext)
}