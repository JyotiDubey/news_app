package com.grab.news.ui.news.viewmodel

import androidx.databinding.ObservableField
import androidx.lifecycle.ViewModel
import io.reactivex.disposables.CompositeDisposable

/**
 * Created by jyotidubey on 2019-03-13.
 */
open class BaseViewModel(protected val disposable: CompositeDisposable) : ViewModel(){

    var isLoading = ObservableField(false)

    fun updateProgress(loading: Boolean){
        isLoading.set(loading)
    }
    override fun onCleared() {
        disposable.dispose()
        super.onCleared()
    }

}