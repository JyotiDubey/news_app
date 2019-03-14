package com.grab.news.ui.news.viewmodel


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.grab.news.data.DataManager
import com.grab.news.data.model.News
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit

/**
 * Created by jyotidubey on 2019-03-09.
 */
class NewsListViewModel(disposable: CompositeDisposable, private val dataManager: DataManager) :
    BaseViewModel(disposable) {

    interface NewsListScreenActionHandler {
        fun onNewsItemClicked(news: News)
        fun onRetryButtonClicked()

    }

    private val newsLiveData: MutableLiveData<MutableList<News>> = MutableLiveData()
    private val progress: MutableLiveData<Boolean> = MutableLiveData()
    private val shouldShowEmptyState: MutableLiveData<Boolean> = MutableLiveData()
    private val networkConnectivityLiveData: MutableLiveData<Boolean> = MutableLiveData()


    init {
        fetchNews()
    }


    fun getNewsLiveData() = newsLiveData as LiveData<List<News>>
    fun getProgressLiveData() = progress as LiveData<Boolean>
    fun getShouldShowEmptyStateLiveData() = shouldShowEmptyState as LiveData<Boolean>
    fun getIsNetworkConnectedStateLiveData() = networkConnectivityLiveData as LiveData<Boolean>

    fun updateNetworkConnectivity(isNetworkConnected: Boolean) {
        this.networkConnectivityLiveData.value = isNetworkConnected
    }
    fun onRetryClick(){
        fetchNews()
    }

    private fun fetchNews() {
        progress.value = true
        disposable.add(
            dataManager.getNews("us", 1)
                .delay(1, TimeUnit.SECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    progress.value = false
                    shouldShowEmptyState.value = it.isEmpty()
                    newsLiveData.value = it as MutableList<News>
                }, {
                })
        )

    }

}