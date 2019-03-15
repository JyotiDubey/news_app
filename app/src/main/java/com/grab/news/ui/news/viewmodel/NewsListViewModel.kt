package com.grab.news.ui.news.viewmodel


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.grab.news.data.DataManager
import com.grab.news.data.model.News
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.processors.PublishProcessor
import io.reactivex.schedulers.Schedulers

/**
 * Created by jyotidubey on 2019-03-09.
 */
class NewsListViewModel(disposable: CompositeDisposable, private val dataManager: DataManager) :
    BaseViewModel(disposable) {

    companion object {
        private const val INITIAL_PAGE_NUMBER = 1
    }

    interface NewsListScreenActionHandler {
        fun onNewsItemClicked(news: News)
        fun onRetryButtonClicked()

    }

    private var pageNumber = 1
    private val pageNumberPublisher = PublishProcessor.create<Int>()
    private val newsLiveData: MutableLiveData<List<News>> = MutableLiveData()


    init {

        createPaginator()
        fetchNewsFromLocal()
        getNewsFromApiAndSyncDB(false)
    }

    fun newsLiveData() = newsLiveData as LiveData<List<News>>


    fun onRequestRetry() {
        getNewsFromApiAndSyncDB(false)
    }

    fun onRequestLoadMore() {
        pageNumberPublisher.onNext(pageNumber)
    }

    fun onRequestRefresh() {
        pageNumber = 1
        getNewsFromApiAndSyncDB(true)
    }


    private fun createPaginator() {
        pageNumberPublisher
            .onBackpressureBuffer()
            .distinctUntilChanged()
            .subscribe { loadNews(it) }

    }

    private fun fetchNewsFromLocal() {
        showProgress(false)
        dataManager.loadNewsFromRepository()
            .filter { it.isNotEmpty() }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                hideProgress(false)
                newsLiveData.value = it
            }
    }

    private fun getNewsFromApiAndSyncDB(refresh: Boolean) {
        showProgress(refresh)
        dataManager.loadNewsFromServer(INITIAL_PAGE_NUMBER)
            .flatMapCompletable {
                shouldShowEmptyState.postValue(it.news.isEmpty())
                dataManager.invalidateAndInsertIntoRepository(it.news)
            }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                hideProgress(refresh)
                pageNumber++
            }, {
                hideProgress(refresh)
                this.shouldShowEmptyState.value = newsLiveData.value == null
            })
    }

    private fun loadNews(page: Int) {
        dataManager.loadNewsFromServer(page)
            .filter { !it.news.isEmpty() }
            .flatMapCompletable {
                pageNumber++
                if (page == INITIAL_PAGE_NUMBER) {
                    dataManager.invalidateAndInsertIntoRepository(it.news)
                } else {
                    dataManager.insertIntoRepository(it.news)
                }
            }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
            }, {
                this.shouldShowEmptyState.value = newsLiveData.value == null
            })


    }


}