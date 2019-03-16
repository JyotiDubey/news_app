package com.grab.news

import com.grab.news.di.DaggerTestComponent
import com.grab.news.di.TestCommonModule
import com.grab.news.di.TestComponent
import com.grab.news.di.TestSchedulerModule


class NewsTestApplication : NewsApplication() {

    val component: TestComponent by lazy {
        DaggerTestComponent.builder()
            .testSchedulerModule(TestSchedulerModule())
            .testCommonModule(TestCommonModule())
            .build()
    }
}

