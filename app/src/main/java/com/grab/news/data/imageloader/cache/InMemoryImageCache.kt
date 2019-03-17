package com.grab.news.data.imageloader.cache

import android.graphics.Bitmap
import android.util.LruCache
import com.grab.news.data.imageloader.ImageLoadParams
import io.reactivex.Completable
import io.reactivex.Maybe
import javax.inject.Inject

/**
 * Created by jyotidubey on 2019-03-17.
 */
class InMemoryImageCache @Inject constructor(): ImageCache {

    override fun put(imageLoadParams: ImageLoadParams): Completable {
        return Completable.fromAction { cache.put(imageLoadParams.url, imageLoadParams.bitmap) }
    }

    override fun get(imageLoadParams: ImageLoadParams): Maybe<Bitmap> {
        return Maybe.fromCallable { cache.get(imageLoadParams.url) }
    }

    override fun clear() {
        cache.evictAll()
    }

    private val cache by lazy {
        // Use a memory cache upto 1/8 of available memory
        val cacheMemory = (Runtime.getRuntime().maxMemory() / 1024) / 8
        object : LruCache<String, Bitmap>(cacheMemory.toInt()) {
            override fun sizeOf(key: String, value: Bitmap): Int {
                return value.byteCount / 1024
            }
        }
    }
}
