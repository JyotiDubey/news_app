package com.grab.news.di

import com.grab.news.scheduler.SchedulerProvider
import dagger.Module
import dagger.Provides
import io.reactivex.Scheduler
import io.reactivex.schedulers.Schedulers
import javax.inject.Singleton

/**
 * Created by jyotidubey on 2019-03-16.
 */
@Module
class TestSchedulerModule {
    @Provides
    @Singleton
    fun schedulerProvider(): SchedulerProvider = MockSchedulerProvider
}

object MockSchedulerProvider : SchedulerProvider {
    override val ui: Scheduler get() = Schedulers.trampoline()
    override val single: Scheduler get() = Schedulers.trampoline()
    override val io: Scheduler get() = Schedulers.trampoline()
    override val pool: Scheduler get() = Schedulers.trampoline()
    override val new: Scheduler get() = Schedulers.trampoline()
}
