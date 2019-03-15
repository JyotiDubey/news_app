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
class NewsListViewModel(private val dataManager: DataManager) : BaseViewModel() {

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
        fetchNewsFromLocal()
        createPaginator()
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
        pageNumber = INITIAL_PAGE_NUMBER
        getNewsFromApiAndSyncDB(true)
    }


    private fun createPaginator() {
        pageNumberPublisher
            .onBackpressureDrop()
            .subscribe {
                loadNews(it)
            }

    }

    private fun fetchNewsFromLocal() {
        showProgress(false)
        disposable.add(dataManager.loadNewsFromRepository()
            .filter { it.isNotEmpty() }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                newsLiveData.value = it
                hideProgress(false)
            })
    }

    private fun getNewsFromApiAndSyncDB(refresh: Boolean) {
        showProgress(refresh)
        disposable.add(dataManager.loadNewsFromServer(INITIAL_PAGE_NUMBER)
            .flatMapCompletable {
                setEmptyState(it.news.isEmpty())
                dataManager.invalidateAndInsertIntoRepository(it.news)
            }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                pageNumber++
                hideProgress(refresh)
            }, {
                hideProgress(refresh)
                setEmptyState(newsLiveData.value == null || newsLiveData?.value?.isEmpty()!!)
            }))
    }

    private fun loadNews(page: Int) {
        showProgress(false)
        disposable.add(dataManager.loadNewsFromServer(page)
            .filter { !it.news.isEmpty() }
            .flatMapCompletable {
                pageNumber++
                if (page == INITIAL_PAGE_NUMBER) {
                    dataManager.invalidateAndInsertIntoRepository(it.news)
                } else {
                    dataManager.insertIntoRepository(it.news)
                }
            }
            .doOnComplete{ hideProgress(false) }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
            }, {
                hideProgress(false)
                setEmptyState(newsLiveData.value == null || newsLiveData?.value?.isEmpty()!!)
            }))


    }


}