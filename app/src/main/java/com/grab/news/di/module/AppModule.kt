package com.grab.news.di.module

import android.app.Application
import android.content.Context
import androidx.lifecycle.ViewModelProvider
import com.grab.news.scheduler.AppSchedulerProvider
import com.grab.news.scheduler.SchedulerProvider
import com.grab.news.ui.news.ViewModelProviderFactory
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * Created by jyotidubey on 2019-03-13.
 */
@Module
class AppModule(val app: Application) {

    @Singleton
    @Provides
    internal fun provideContext(): Context = app

    @Singleton
    @Provides
    internal fun provideApplication(): Application = app


    @Singleton
    @Provides
    internal fun provideVMProviderFactory(factory: ViewModelProviderFactory): ViewModelProvider.NewInstanceFactory =
        factory

    @Singleton
    @Provides
    internal fun provideAppScheduler(schedulerProvider: AppSchedulerProvider): SchedulerProvider = schedulerProvider

}