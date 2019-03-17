package com.grab.news.di.module

import com.grab.news.data.imageloader.ImageLoader
import com.grab.news.data.imageloader.cache.ImageCache
import com.grab.news.data.imageloader.cache.InMemoryImageCache
import com.grab.news.di.imageloader.internal.DefaultImageLoader
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * Created by jyotidubey on 2019-03-17.
 */
@Module
class ImageLoaderModule{

    @Singleton
    @Provides
    internal fun provideImageCache(cache: InMemoryImageCache): ImageCache = cache

    @Singleton
    @Provides
    internal fun provideLoader(imageLoader: DefaultImageLoader): ImageLoader = imageLoader
}