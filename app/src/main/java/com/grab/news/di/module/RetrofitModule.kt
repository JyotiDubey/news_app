package com.grab.news.di.module

import com.grab.news.BuildConfig
import com.grab.news.data.remote.retrofit.NewsService
import com.grab.news.di.BaseUrlInfo
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

/**
 * Created by jyotidubey on 2019-03-15.
 */
@Module
class RetrofitModule {

    @Provides
    @Singleton
    internal fun provideApiInterface(retrofit: Retrofit): NewsService {
        return retrofit.create<NewsService>(NewsService::class.java)
    }

    @Provides
    @BaseUrlInfo
    internal fun provideBaseUrl() = BuildConfig.BASE_URL

    @Provides
    @Singleton
    internal fun provideRetrofit(okHttpClient: OkHttpClient, @BaseUrlInfo url: String): Retrofit {
        return Retrofit.Builder()
            .baseUrl(url)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .client(okHttpClient)
            .build()
    }

    @Provides
    @Singleton
    internal fun provideOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .build()
    }

}