package com.grab.news

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.google.common.truth.Truth
import com.grab.news.data.DataManager
import com.grab.news.data.NewsDataManager
import com.grab.news.ui.news.viewmodel.NewsListViewModel
import io.reactivex.disposables.CompositeDisposable
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnit
import org.mockito.junit.MockitoRule

/**
 * Created by jyotidubey on 2019-03-15.
 */
class NewsListViewModelTest {

    @get:Rule
    val mockitoRule: MockitoRule = MockitoJUnit.rule()

    @get:Rule
    val taskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val rxSchedulerRule = RxScheduleRule()

    private var mockManager =  NewsDataManager()

    private var mockdisposible: CompositeDisposable = CompositeDisposable()

    private lateinit var viewModel: NewsListViewModel

    @Before
    fun setupNewsViewModel() {
        MockitoAnnotations.initMocks(this)
        viewModel = NewsListViewModel(mockdisposible, mockManager)
    }

    @Test
    fun `init set news list to empty`() {
        val news = viewModel.newsLiveData().testObserver()

        Truth.assert_()
            .that(news.observedValues).isEmpty()
    }


}