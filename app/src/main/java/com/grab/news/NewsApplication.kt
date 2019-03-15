package com.grab.news

import android.app.Application
import android.content.Context
import com.grab.news.di.component.AppComponent
import com.grab.news.di.component.DaggerAppComponent
import com.grab.news.di.module.AppModule
import com.grab.news.di.module.RetrofitModule

/**
 * Created by jyotidubey on 2019-03-13.
 */
open class NewsApplication:Application(){

    open val appComponent: AppComponent by lazy {
        DaggerAppComponent.builder()
            .appModule(AppModule(this))
            .retrofitModule(RetrofitModule(this))
            .build()
    }
    companion object {
        fun get(context: Context): NewsApplication {
            return context.applicationContext as NewsApplication
        }
    }


}