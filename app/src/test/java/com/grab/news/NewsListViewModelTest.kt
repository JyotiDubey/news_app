package com.grab.news

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.google.common.truth.Truth
import com.grab.news.data.DataManager
import com.grab.news.ui.news.viewmodel.NewsListViewModel
import io.reactivex.disposables.CompositeDisposable
import org.junit.Rule
import org.junit.Test
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.junit.MockitoJUnit
import org.mockito.junit.MockitoRule

/**
 * Created by jyotidubey on 2019-03-15.
 */
class NewsListViewModelTest{

    @get:Rule
    val mockitoRule: MockitoRule = MockitoJUnit.rule()

    @get:Rule
    val taskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val rxSchedulerRule = RxScheduleRule()

    @Mock
    private lateinit var mockManager: DataManager

    private var mockdisposible: CompositeDisposable = CompositeDisposable()

    @InjectMocks
    private lateinit var classUnderTest: NewsListViewModel

    @Test
    fun `init set joke list to empty`() {
        val news = classUnderTest.newsLiveData().testObserver()

        Truth.assert_()
            .that(news.observedValues.first()).isEmpty()
    }



}