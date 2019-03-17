package com.grab.news.data.imageloader.cache

import android.graphics.Bitmap
import com.grab.news.data.imageloader.ImageLoadParams
import io.reactivex.Completable
import io.reactivex.Maybe

/**
 * Created by jyotidubey on 2019-03-17.
 */

interface ImageCache{

     fun put(imageLoadParams: ImageLoadParams) : Completable

     fun get(imageLoadParams: ImageLoadParams) : Maybe<Bitmap>

     fun clear()
}