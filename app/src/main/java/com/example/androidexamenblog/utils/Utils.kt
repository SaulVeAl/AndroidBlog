package com.example.androidexamenblog.utils

import android.app.Activity
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.view.View
import androidx.core.view.isVisible
import com.agrawalsuneet.dotsloader.loaders.TrailingCircularDotsLoader
import java.text.SimpleDateFormat
import java.util.Calendar

object Utils {

    fun isNetworkAvailable(context: Context?): Boolean {
        if (context == null) return false
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            val capabilities =
                connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
            if (capabilities != null) {
                when {
                    capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> {
                        return true
                    }
                    capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> {
                        return true
                    }
                    capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> {
                        return true
                    }
                }
            }
        } else {
            val activeNetworkInfo = connectivityManager.activeNetworkInfo
            if (activeNetworkInfo != null && activeNetworkInfo.isConnected) {
                return true
            }
        }
        return false
    }

    fun convertDate(l: Long, format: String): String {
        var sdf = SimpleDateFormat(format)
        var calendar = Calendar.getInstance()
        calendar.timeInMillis = l
        return sdf.format(calendar.time)
    }

    fun loader(activity: Activity, loader: TrailingCircularDotsLoader?, show: Boolean){

        if (!activity.isDestroyed) {
            if (show) {
                if (loader != null) {
                    if (!loader.isVisible)
                        loader.visibility = View.VISIBLE
                }
            } else {
                if (loader != null) {
                    if (loader.isVisible)
                        loader.visibility = View.GONE
                }
            }
        }

    }

}