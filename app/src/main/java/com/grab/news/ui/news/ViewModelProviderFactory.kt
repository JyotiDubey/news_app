package com.grab.news.ui.news

/**
 * Created by jyotidubey on 2019-03-13.
 */

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.grab.news.data.DataManager
import com.grab.news.scheduler.SchedulerProvider
import com.grab.news.ui.news.viewmodel.NewsDetailViewModel
import com.grab.news.ui.news.viewmodel.NewsListViewModel
import javax.inject.Inject

/**
 * Created by jyotidubey on 2019-03-13.
 */
class ViewModelProviderFactory @Inject constructor(private val schedulerProvider: SchedulerProvider,private val dataManager: DataManager
) : ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(NewsListViewModel::class.java)) {
            return NewsListViewModel(schedulerProvider,dataManager) as T
        } else if (modelClass.isAssignableFrom(NewsDetailViewModel::class.java)) {
            return NewsDetailViewModel() as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
    }
}