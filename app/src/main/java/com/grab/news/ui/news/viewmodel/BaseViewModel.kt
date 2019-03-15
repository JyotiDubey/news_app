package com.grab.news.ui.news.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.disposables.CompositeDisposable

/**
 * Created by jyotidubey on 2019-03-13.
 */
open class BaseViewModel(private val disposable: CompositeDisposable) : ViewModel(){

    private val shouldShowEmptyState: MutableLiveData<Boolean> = MutableLiveData()
    private val refreshState: MutableLiveData<Boolean> = MutableLiveData()
    private val progress: MutableLiveData<Boolean> = MutableLiveData()
    private val isDeviceOfflineState: MutableLiveData<Boolean> = MutableLiveData()

    fun emptyStateLiveData() = shouldShowEmptyState as LiveData<Boolean>
    fun isDeviceOfflineStateLiveData() = isDeviceOfflineState as LiveData<Boolean>
    fun progressLiveData() = progress as LiveData<Boolean>
    fun refreshLiveData() = refreshState as LiveData<Boolean>


    fun onNetworkConnectivityChanged(deviceNetworkState: Boolean) {
        isDeviceOfflineState.value = deviceNetworkState
    }

    fun showProgress(refresh:Boolean){
        if (refresh) {
            refreshState.postValue( true)
        } else {
            progress.postValue(true)
        }
    }

    fun setEmptyState(empty:Boolean) {
        shouldShowEmptyState.postValue(empty)
    }

    fun hideProgress(refresh:Boolean){
        if (refresh) {
            refreshState.postValue(false)
        } else {
            progress.postValue(false)
        }
    }

    override fun onCleared() {
        disposable.dispose()
        super.onCleared()
    }

}