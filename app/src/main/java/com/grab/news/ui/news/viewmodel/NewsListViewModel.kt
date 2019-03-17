package com.grab.news.ui.news.viewmodel


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.grab.news.data.DataManager
import com.grab.news.data.model.News
import com.grab.news.exception.PageLimitExceeded
import com.grab.news.scheduler.SchedulerProvider
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.processors.PublishProcessor
import io.reactivex.schedulers.Schedulers

/**
 * Created by jyotidubey on 2019-03-09.
 */
class NewsListViewModel(private val schedulerProvider: SchedulerProvider, private val dataManager: DataManager) :
    BaseViewModel() {

    companion object {
        private const val INITIAL_PAGE_NUMBER = 1
    }

    interface NewsListScreenActionHandler {
        fun onNewsItemClicked(news: News)
        fun onRetryButtonClicked()
    }

    private var pageNumber = INITIAL_PAGE_NUMBER
    private val pageNumberPublisher = PublishProcessor.create<Int>()
    private val newsLiveData: MutableLiveData<List<News>> = MutableLiveData()

    init {
        createPaginator()
        fetchNewsFromLocal()
        onRequestLoadMore()
    }

    fun newsLiveData() = newsLiveData as LiveData<List<News>>


    fun onRequestRetry() {
        showProgress(false)
        pageNumber = INITIAL_PAGE_NUMBER
        onRequestLoadMore()
    }


    fun onRequestRefresh() {
        showProgress(true)
        pageNumber = INITIAL_PAGE_NUMBER
        onRequestLoadMore()
    }
    fun onRequestLoadMore() {
        pageNumberPublisher.onNext(pageNumber)
    }
    private fun createPaginator() {
        disposable.add(pageNumberPublisher
            .observeOn(Schedulers.computation())
            .onBackpressureDrop()
            .concatMapSingle { page -> dataManager.loadNewsFromServer(page) }
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                if (it.isFailure) {
                    it.exceptionOrNull()?.let { exception ->
                        if (exception is PageLimitExceeded) {
                        } else {
                            setEmptyState()
                        }
                    }
                } else {
                    pageNumber++

                }
                hideProgress(refresh = false)
            })
    }

    private fun fetchNewsFromLocal() {
        showProgress(false)
        disposable.add(dataManager.loadNewsFromRepository()
            .compose(schedulerProvider.ioToUiFlowableSchedulers())
            .subscribe {
                newsLiveData.value = it
                setEmptyState()
                hideProgress(false)
            })
    }

    private fun setEmptyState() = setEmptyState(newsLiveData.value == null || newsLiveData?.value?.isEmpty()!!)


}