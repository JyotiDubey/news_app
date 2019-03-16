package com.grab.news

import com.grab.news.ui.news.viewmodel.NewsListViewModel
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import javax.inject.Inject

/**
 * Created by jyotidubey on 2019-03-15.
 */
class NewsListViewModelTest : NewsRobolectricTestSuite() {

    @Inject
    internal lateinit var viewModel: NewsListViewModel

    @Before
    fun setUp() {
        testAppComponent.inject(this)
    }

    @Test
    fun assertInjected() {
        Assert.assertTrue(::viewModel.isInitialized)
        Assert.assertTrue(viewModel.newsLiveData()?.value?.isEmpty()!!)

    }
}