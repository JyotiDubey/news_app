package com.grab.news.di

import java.lang.annotation.Retention
import java.lang.annotation.RetentionPolicy
import javax.inject.Qualifier

/**
 * Created by jyotidubey on 2019-03-15.
 */
@Qualifier
@Retention(RetentionPolicy.RUNTIME)
annotation class DatabaseInfo