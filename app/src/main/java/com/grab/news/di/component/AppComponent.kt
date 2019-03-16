package com.grab.news.di.component

import android.app.Application
import com.grab.news.di.module.AppModule
import com.grab.news.di.module.NewsListModule
import com.grab.news.di.module.RetrofitModule
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
@Component(modules = [AppModule::class, RetrofitModule::class])
interface AppComponent{

    fun plus(module: NewsListModule): NewsListComponent

    fun plus() : NewsDetailComponent

    @ActivityScope
    @Subcomponent(modules = [NewsListModule::class])
    interface NewsListComponent{
        fun inject(activity: NewsListActivity)
    }

    @ActivityScope
    @Subcomponent
    interface NewsDetailComponent{
        fun inject(activity: NewsDetailActivity)
    }
}
