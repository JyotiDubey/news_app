package com.grab.news.ui.news.viewmodel

import androidx.databinding.ObservableField
import androidx.lifecycle.ViewModel
import io.reactivex.disposables.CompositeDisposable

/**
 * Created by jyotidubey on 2019-03-13.
 */
open class BaseViewModel(protected val disposable: CompositeDisposable) : ViewModel(){

    var loadingState = ObservableField(true)
    var emptyViewState = ObservableField(false)
    var isDeviceOnline = ObservableField(false)

    fun updateProgress(loading: Boolean){
        loadingState.set(loading)
    }

    fun updateEmptyView(shouldShowEmptyView: Boolean){
        emptyViewState.set(shouldShowEmptyView)
    }

    fun updateNetworkConnectivityView(isNetworkConnected: Boolean){
        isDeviceOnline.set(isNetworkConnected)
    }
    override fun onCleared() {
        disposable.dispose()
        super.onCleared()
    }

}