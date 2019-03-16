package com.grab.news.di

import com.grab.news.NewsListViewModelTest
import com.grab.news.di.component.AppComponent
import dagger.Component
import javax.inject.Singleton

/**
 * Created by jyotidubey on 2019-03-15.
 */
@Singleton
@Component(modules = [TestSchedulerModule::class, TestCommonModule::class])
interface TestComponent {

    fun inject(newsListViewModelTest: NewsListViewModelTest)


}
