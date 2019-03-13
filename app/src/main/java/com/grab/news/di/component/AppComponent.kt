package com.grab.news.di.component

import com.grab.news.di.module.AppModule
import com.grab.news.di.module.NewsListModule
import com.grab.news.di.scope.ActivityScope
import com.grab.news.ui.news.NewsListActivity
import dagger.Component
import dagger.Subcomponent
import javax.inject.Singleton

/**
 * Created by jyotidubey on 2019-03-13.
 */
@Singleton
@Component(modules = [AppModule::class])
interface AppComponent{

    fun plus(module: NewsListModule): NewsListComponent

    @ActivityScope
    @Subcomponent(modules = [NewsListModule::class])
    interface NewsListComponent{
        fun inject(activity: NewsListActivity)
    }

}
