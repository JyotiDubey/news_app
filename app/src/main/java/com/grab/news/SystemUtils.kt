package com.grab.news

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.util.Log
import androidx.annotation.NonNull
import androidx.annotation.Nullable

/**
 * Created by jyotidubey on 2019-03-13.
 */

class SystemUtils {
    companion object {
        val TAG: String = SystemUtils::class.java.name
        /**
         * Open the given url in the external browser.
         */
        fun openUrlInBrowser(@NonNull context: Context, @Nullable url: String?) {
            val intent = Intent(Intent.ACTION_VIEW)
            url?.let {
                intent.data = Uri.parse(url)
                try {
                    context.startActivity(intent)
                } catch (exp: ActivityNotFoundException) {
                    Log.wtf(TAG, exp)
                }
            } ?: Log.e(TAG, "Unable to open url in browser")
        }
    }
}
