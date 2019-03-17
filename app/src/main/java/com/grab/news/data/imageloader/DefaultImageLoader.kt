package com.grab.news.di.imageloader.internal

import android.graphics.BitmapFactory
import android.graphics.drawable.Drawable
import android.widget.ImageView
import com.grab.news.data.imageloader.ImageLoadParams
import com.grab.news.data.imageloader.ImageLoader
import com.grab.news.data.imageloader.cache.ImageCache
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.processors.PublishProcessor
import io.reactivex.schedulers.Schedulers
import okhttp3.OkHttpClient
import okhttp3.Request
import java.lang.ref.WeakReference
import javax.inject.Inject

/**
 * Created by jyotidubey on 2019-03-17.
 */
class DefaultImageLoader @Inject constructor(private val okHttp : OkHttpClient, private val cache: ImageCache ) :
    ImageLoader {

    private val disposable = CompositeDisposable()
    private val loadRequestPublisher = PublishProcessor.create<ImageLoadParams>()

    init {
        createLoadRequestPublisher()
    }

    override fun loadImage(image: ImageView, url: String, fallbackDrawable: Drawable?){
        loadRequestPublisher.onNext(ImageLoadParams(url = url, imageView = WeakReference(image), fallbackDrawable = fallbackDrawable))
    }

    override fun clear() {
        disposable.clear()
        cache.clear()
    }

    private fun createLoadRequestPublisher() {
        disposable.add(loadRequestPublisher
            .onBackpressureDrop()
            .flatMapSingle { imageLoadParams ->
                loadImage(imageLoadParams)
                    .subscribeOn(Schedulers.io())
            }
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { imageLoadParams ->
                imageLoadParams.run {
                    if (imageLoadParams.bitmap != null) {
                        imageView.get()?.setImageBitmap(bitmap)
                    }else{
                        imageView.get()?.setImageDrawable(fallbackDrawable)
                    }

                }
            })
    }

    private fun loadImage(imageLoadParams: ImageLoadParams): Single<ImageLoadParams> {
        val networkLoader = Single
            .create<ImageLoadParams> { emitter ->
                try {
                    val imageView = imageLoadParams.imageView.get()
                        ?: throw IllegalStateException("ImageView garbage collected")
                    val request = Request.Builder().url(imageLoadParams.url).build()
                    okHttp.newCall(request).apply {
                        execute().use { response ->
                            val options = BitmapFactory.Options()
                                options.inJustDecodeBounds = true
                            calculateInSampleSize(options, imageView.width, imageView.height)
                            val bitmap = BitmapFactory.decodeStream(response.body()?.byteStream())
                            emitter.onSuccess(imageLoadParams.copy(bitmap = bitmap))
                        }
                    }

                } catch (e: Exception) {
                    emitter.onSuccess(imageLoadParams)
                }
            }
            .onErrorReturnItem(imageLoadParams)
            .flatMap { imageLoadParams -> cache.put(imageLoadParams).toSingleDefault(imageLoadParams) }
            .subscribeOn(Schedulers.io())

        return cache.get(imageLoadParams)
            .map { imageLoadParams }
            .switchIfEmpty(networkLoader)

    }

    private fun calculateInSampleSize(options: BitmapFactory.Options, reqWidth: Int, reqHeight: Int): Int {
        val (height, width) = options.run { outHeight to outWidth }
        var inSampleSize = 1
        if (height > reqHeight || width > reqWidth) {
            val halfHeight = height / 2
            val halfWidth = width / 2
            while (halfHeight / inSampleSize >= reqHeight && halfWidth / inSampleSize >= reqWidth) {
                inSampleSize *= 2
            }
        }
        return inSampleSize
    }




}