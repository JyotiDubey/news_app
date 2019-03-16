package com.grab.news.ui.news.viewmodel


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.grab.news.data.DataManager
import com.grab.news.data.model.News
import com.grab.news.scheduler.SchedulerProvider
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.exceptions.Exceptions
import io.reactivex.processors.PublishProcessor
import io.reactivex.schedulers.Schedulers

/**
 * Created by jyotidubey on 2019-03-09.
 */
class NewsListViewModel(private val schedulerProvider: SchedulerProvider,private val dataManager: DataManager) : BaseViewModel() {

    companion object {
        private const val INITIAL_PAGE_NUMBER = 1
        private val TAG = NewsListViewModel::class.java.simpleName
    }

    interface NewsListScreenActionHandler {
        fun onNewsItemClicked(news: News)
        fun onRetryButtonClicked()
    }

    private var pageNumber = INITIAL_PAGE_NUMBER
    private val pageNumberPublisher = PublishProcessor.create<Int>()
    private val newsLiveData: MutableLiveData<List<News>> = MutableLiveData()

    init {
        fetchNewsFromLocal()
        createPaginator()
        onRequestLoadMore()
    }

    fun newsLiveData() = newsLiveData as LiveData<List<News>>


    fun onRequestRetry() {
        showProgress(false)
        pageNumber = INITIAL_PAGE_NUMBER
        onRequestLoadMore()
    }

    fun onRequestLoadMore() {
        pageNumberPublisher.onNext(pageNumber)
    }

    fun onRequestRefresh() {
        showProgress(true)
        pageNumber = INITIAL_PAGE_NUMBER
        onRequestLoadMore()
    }

    private fun createPaginator() {
        pageNumberPublisher
            .observeOn(Schedulers.computation())
            .onBackpressureDrop()
            .concatMapSingle { page ->
                dataManager.loadNewsFromServer(page)
                    .map { newsListResponse ->
                        if (newsListResponse.news.isEmpty()) {
                            throw Exceptions.propagate(PageLimitExceeded())
                        } else {
                            newsListResponse.news
                        }
                    }.flatMapCompletable { news ->
                        return@flatMapCompletable dataManager.updateRepository(page, news)
                    }.toSingle { 1 }
                    .map { Result.success(1) }
                    .onErrorReturn { Result.failure(it) }
                    .subscribeOn(Schedulers.io())
            }.observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                hideProgress(refresh = false)
                if (it.isFailure) {
                    it.exceptionOrNull()?.let { exception ->
                        if (exception is PageLimitExceeded) {
                        } else {
                            setEmptyState(newsLiveData.value == null || newsLiveData?.value?.isEmpty()!!)
                        }
                    }
                } else {
                    pageNumber++

                }
            }
    }

    private fun fetchNewsFromLocal() {
        showProgress(false)
        dataManager.loadNewsFromRepository()
            .compose(schedulerProvider.ioToUiFlowableSchedulers())
            .subscribe {
                setEmptyState(it.isEmpty())
                newsLiveData.value = it
                hideProgress(false)
            }
    }

    class PageLimitExceeded : java.lang.RuntimeException()
}