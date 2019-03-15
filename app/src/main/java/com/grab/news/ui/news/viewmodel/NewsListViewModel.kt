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
        onRequestLoadMore()
    }

    fun newsLiveData() = newsLiveData as LiveData<List<News>>


    fun onRequestRetry() {
        pageNumber = INITIAL_PAGE_NUMBER
        onRequestLoadMore()
    }

    fun onRequestLoadMore() {
        pageNumberPublisher.onNext(pageNumber)
    }

    fun onRequestRefresh() {
        pageNumber = INITIAL_PAGE_NUMBER
        onRequestLoadMore()
    }


    private fun createPaginator() {
        pageNumberPublisher
            .observeOn(Schedulers.computation())
            .concatMapCompletable {
                showProgress(false)
                return@concatMapCompletable dataManager.loadNewsFromServer(it).subscribeOn(Schedulers.io())
            }
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                hideProgress(false)
                pageNumber++
            }, {
                hideProgress(false)
                setEmptyState(newsLiveData.value == null || newsLiveData?.value?.isEmpty()!!)
            })


    }

    private fun fetchNewsFromLocal() {
        disposable.add(dataManager.loadNewsFromRepository()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                newsLiveData.value = it
            })
    }



}