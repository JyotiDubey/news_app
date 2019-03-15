package com.grab.news

import com.grab.news.di.component.AppComponent



class NewsTestApplication : NewsApplication() {

    override val appComponent: AppComponent by lazy {
        DaggerTestComponent
                .builder()
                .application(this)
                .build()
    }

}