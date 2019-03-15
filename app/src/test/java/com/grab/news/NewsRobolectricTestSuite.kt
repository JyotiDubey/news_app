package com.grab.news

import android.app.Application
import com.grab.news.di.TestComponent
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.RuntimeEnvironment
import org.robolectric.annotation.Config


@RunWith(RobolectricTestRunner::class)
@Config(sdk = [19, 21, 27], application = NewsTestApplication::class)
abstract class NewsRobolectricTestSuite {

    val application: Application get() = RuntimeEnvironment.application

    /**
     * Test component to inject app level dependencies
     */
    val testAppComponent: TestComponent by lazy {
        (application as NewsTestApplication).appComponent as TestComponent
    }


}