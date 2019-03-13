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

    interface NewItemClickHandler {
        fun onNewsItemClicked(news: News)
    }

    private val newsLiveData: MutableLiveData<List<News>> = MutableLiveData()
    private val progress: MutableLiveData<Boolean> = MutableLiveData()

    init {
        fetchNews()
    }

    fun getNewsLiveData() = newsLiveData as LiveData<List<News>>
    fun getProgressLiveData() = progress as LiveData<Boolean>

    private fun fetchNews() {
        disposable.add(dataManager.getNews("us", 1)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                progress.value = false
                newsLiveData.value = it
            }, {
            })
        )

    }




}