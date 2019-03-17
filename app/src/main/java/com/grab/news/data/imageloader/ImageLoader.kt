package com.grab.news.data.imageloader

import android.graphics.drawable.Drawable
import android.widget.ImageView

interface ImageLoader {

    fun loadImage(image: ImageView, url: String, fallbackDrawable: Drawable?)

    fun clear()
}
