package com.grab.news.scheduler

import io.reactivex.Flowable
import io.reactivex.FlowableTransformer
import io.reactivex.Scheduler

/**
 * Created by jyotidubey on 2019-03-16.
 */
interface SchedulerProvider {
    /**
     * Scheduler for interacting with UI thread.
     */
    val ui: Scheduler

    /**
     * Scheduler with single shared background thread. Recommended for serial executions (queue)
     */
    val single: Scheduler

    /**
     * Scheduler for doing network/io bound work.
     */
    val io: Scheduler

    /**
     * Scheduler for doing computationally intensive work.
     */
    val pool: Scheduler

    /**
     * Scheduler backed by a new single thread created for each unit of work
     */
    val new: Scheduler

    /**
     * Transforms upstream [Flowable] into another [Flowable] with subscription on [io] and observation
     * on [ui]
     */
    fun <T> ioToUiFlowableSchedulers(): FlowableTransformer<T, T> {
        return FlowableTransformer { upstream ->
            upstream.subscribeOn(io).observeOn(ui)
        }
    }

}