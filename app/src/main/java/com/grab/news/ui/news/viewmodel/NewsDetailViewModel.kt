package com.grab.news.ui.news.viewmodel

import io.reactivex.disposables.CompositeDisposable

/**
 * Created by jyotidubey on 2019-03-09.
 */
class NewsDetailViewModel(disposable: CompositeDisposable) : BaseViewModel(disposable) {

    fun showProgress() {
        progress.value = true
    }

    fun hideProgress() {
        progress.value = false
    }
}