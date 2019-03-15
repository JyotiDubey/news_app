package com.grab.news

import com.grab.news.di.component.AppComponent
import com.grab.news.di.module.AppModule
import dagger.Component
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

/**
 * Created by jyotidubey on 2019-03-15.
 */
@Singleton
@Component(modules = [ AndroidSupportInjectionModule::class,TestCommonModule::class,AppModule::class])
interface TestAppComponent : AppComponent{

}