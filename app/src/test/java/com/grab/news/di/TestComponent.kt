package com.grab.news.di

import android.app.Application
import com.grab.news.NewsListViewModelTest
import com.grab.news.di.component.AppComponent
import com.grab.news.di.module.AppModule
import com.grab.news.ui.news.viewmodel.NewsListViewModel
import dagger.BindsInstance
import dagger.Component
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

/**
 * Created by jyotidubey on 2019-03-15.
 */
@Singleton
@Component(modules = [AndroidSupportInjectionModule::class, TestCommonModule::class, TestSchedulerModule::class, AppModule::class])
interface TestComponent : AppComponent{

    fun inject(newsListViewModelTest: NewsListViewModelTest)

    @Component.Builder
    interface Builder {
        fun build(): TestComponent
        @BindsInstance
        fun application(application: Application): Builder
    }

}
