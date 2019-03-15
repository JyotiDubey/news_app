package com.grab.news

import com.grab.news.data.DataManager
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * Created by jyotidubey on 2019-03-15.
 */
@Module
abstract class TestCommonModule {
    @Module
    companion object {
        @Provides
        @Singleton
        @JvmStatic
        fun dataManager(): DataManager = MockDataManager()
    }
}