package com.grab.news.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.grab.news.data.DataManager
import com.grab.news.data.model.News
import com.grab.news.scheduler.SchedulerProvider
import com.grab.news.ui.news.viewmodel.NewsDetailViewModel
import com.grab.news.ui.news.viewmodel.NewsListViewModel
import dagger.Module
import dagger.Provides
import io.reactivex.Flowable
import io.reactivex.Single
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Created by jyotidubey on 2019-03-15.
 */
@Module
class TestCommonModule {

    @Singleton
    @Provides
    internal fun provideDataManager(): DataManager = MockDataManager


    @Singleton
    @Provides
    internal fun provideViewModel(
        mockedDataManager: DataManager,
        mockedSchedulerProvider: SchedulerProvider
    ): NewsListViewModel {
        return NewsListViewModel(mockedSchedulerProvider, mockedDataManager)
    }
}

object MockDataManager : DataManager {
    override fun loadNewsFromServer(page: Int): Single<Result<Int>> {
        return Single.just(Result.success(1)).delay(5,TimeUnit.SECONDS)
    }

    override fun loadNewsFromRepository(): Flowable<List<News>> {
        return Flowable.just(mutableListOf())
    }
}

class MockViewModelProviderFactory @Inject constructor(
    private val schedulerProvider: SchedulerProvider, private val dataManager: DataManager
) : ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(NewsListViewModel::class.java)) {
            return NewsListViewModel(schedulerProvider, dataManager) as T
        } else if (modelClass.isAssignableFrom(NewsDetailViewModel::class.java)) {
            return NewsDetailViewModel() as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
    }
}