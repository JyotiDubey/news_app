package com.grab.news.di.module

import com.grab.news.di.scope.ActivityScope
import com.grab.news.ui.news.NewsListAdapter
import com.grab.news.ui.news.viewmodel.NewsListViewModel
import dagger.Module
import dagger.Provides

/**
 * Created by jyotidubey on 2019-03-13.
 */
@Module
class NewsListModule(private val handler: NewsListViewModel.NewsListScreenActionHandler){

    @ActivityScope
    @Provides
    internal fun provideNewsListAdapter() = NewsListAdapter(handler)

}