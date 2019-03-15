package com.grab.news.scheduler

import io.reactivex.Scheduler
import javax.inject.Inject
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/**
 * Created by jyotidubey on 2019-03-16.
 */
class AppSchedulerProvider
@Inject
constructor() : SchedulerProvider {
    override val ui: Scheduler get() = AndroidSchedulers.mainThread()
    override val single: Scheduler get() = Schedulers.single()
    override val io: Scheduler get() = Schedulers.io()
    override val pool: Scheduler get() = Schedulers.computation()
    override val new: Scheduler get() = Schedulers.newThread()
}
