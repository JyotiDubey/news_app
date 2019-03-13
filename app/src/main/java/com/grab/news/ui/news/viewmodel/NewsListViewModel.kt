package com.grab.news.ui.news.viewmodel


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.grab.news.data.DataManager
import com.grab.news.data.model.News
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.BehaviorSubject

/**
 * Created by jyotidubey on 2019-03-09.
 */
class NewsListViewModel(disposable: CompositeDisposable, private val dataManager: DataManager) :
    BaseViewModel(disposable) {

    interface NewItemClickHandler {
        fun onNewsItemClicked(news: News)
    }

    private var pageNumber = 2
    private var paginator: BehaviorSubject<Boolean> = BehaviorSubject.create()
    private val newsLiveData: MutableLiveData<MutableList<News>> = MutableLiveData()
    private val progress: MutableLiveData<Boolean> = MutableLiveData()

    init {
        fetchNews()
        paginator.observeOn(AndroidSchedulers.mainThread())
            .subscribe { loadNews() }

    }

    fun getNewsLiveData() = newsLiveData as LiveData<List<News>>
    fun getProgressLiveData() = progress as LiveData<Boolean>

    fun loadNextPage() = paginator.onNext(true)

    private fun fetchNews() {
        disposable.add(
            dataManager.getNews("us", 1)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    progress.value = false
                    val currentNews = newsLiveData.value ?: mutableListOf()
                    currentNews.addAll(0,it)
                    newsLiveData.value = currentNews
                }, {
                })
        )

    }

    private fun loadNews() {
        disposable.add(dataManager.loadNewsFromApi("us", pageNumber)
            .filter {
                it.news?.size != 0
            }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                pageNumber++
                progress.value = false
                val currentNews = newsLiveData.value ?: mutableListOf()
                currentNews.addAll(it.news as List<News>)
                newsLiveData.value = currentNews
            }, {
                System.out.println("fgshdjkyluip[o")
            })
        )

    }
}