package com.grab.news

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.google.common.truth.Truth
import com.grab.news.data.DataManager
import com.grab.news.data.NewsDataManager
import com.grab.news.ui.news.viewmodel.NewsListViewModel
import io.reactivex.disposables.CompositeDisposable
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnit
import org.mockito.junit.MockitoRule
import javax.inject.Inject

/**
 * Created by jyotidubey on 2019-03-15.
 */
class NewsListViewModelTest : NewsRobolectricTestSuite() {

    @Inject
    lateinit var newsListViewModel: NewsListViewModel

    @Before
    fun setUp() {
        testAppComponent.inject(this)
    }

    @Test
    fun assertInjected() {
        Assert.assertTrue(::newsListViewModel.isInitialized)
    }
}