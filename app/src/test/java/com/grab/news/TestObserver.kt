package com.grab.news

import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer

/**
 * Created by jyotidubey on 2019-03-15.
 */
open class TestObserver<T> : Observer<T> {

    val observedValues = mutableListOf<T?>()

    override fun onChanged(value: T?) {
        observedValues.add(value)
    }
}

fun <T> LiveData<T>.testObserver() = TestObserver<T>().also {
    observeForever(it)
}