package com.grab.news.data.imageloader

import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.widget.ImageView
import java.lang.ref.WeakReference

/**
 * Created by jyotidubey on 2019-03-17.
 */
data class ImageLoadParams(val imageView: WeakReference<ImageView>, val url : String, val fallbackDrawable: Drawable?,val bitmap: Bitmap? = null)