package com.grab.news.di.component

import com.grab.news.di.module.*
import com.grab.news.di.scope.ActivityScope
import com.grab.news.ui.news.NewsDetailActivity
import com.grab.news.ui.news.NewsListActivity
import dagger.Component
import dagger.Subcomponent
import javax.inject.Singleton

/**
 * Created by jyotidubey on 2019-03-13.
 */
@Singleton
@Component(modules = [AppModule::class, RetrofitModule::class, RepositoryModule::class, ImageLoaderModule::class])
interface AppComponent{

    fun plus(module: NewsListModule): NewsListComponent

    fun inject(activity: NewsDetailActivity)

    @ActivityScope
    @Subcomponent(modules = [NewsListModule::class])
    interface NewsListComponent{
        fun inject(activity: NewsListActivity)
    }
}
