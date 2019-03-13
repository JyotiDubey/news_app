package com.grab.news.ui.news.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import io.reactivex.disposables.CompositeDisposable

/**
 * Created by jyotidubey on 2019-03-09.
 */
class NewsDetailViewModel(disposable: CompositeDisposable) : BaseViewModel(disposable) {
    private val progress: MutableLiveData<Boolean> = MutableLiveData()

    fun getProgressLiveData() = progress as LiveData<Boolean>

    fun showProgress() {
        progress.value = true
    }

    fun hideProgress() {
        progress.value = false
    }
}