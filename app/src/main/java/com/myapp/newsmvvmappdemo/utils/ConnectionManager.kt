package com.myapp.newsmvvmappdemo.utils

import android.annotation.SuppressLint
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities

/** ConnectivityManager is used to query the state of network. */
object ConnectionManager {

    enum class ConnectivityMode {
        NONE,
        MOBILE,
        WIFI,
        OTHER
    }

    @SuppressLint("MissingPermission")
    private fun checkConnectivity(context: Context): ConnectivityMode {
        val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager?

        cm?.run {
            getNetworkCapabilities(activeNetwork)?.run {
                return when {
                    hasTransport(NetworkCapabilities.TRANSPORT_WIFI) || hasTransport(
                        NetworkCapabilities.TRANSPORT_VPN
                    ) -> ConnectivityMode.WIFI
                    hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> ConnectivityMode.MOBILE
                    hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> ConnectivityMode.OTHER
                    hasTransport(NetworkCapabilities.TRANSPORT_BLUETOOTH) -> ConnectivityMode.OTHER
                    else -> ConnectivityMode.NONE
                }
            }
        }

        return ConnectivityMode.NONE
    }

    fun userOnline(mContext: Context) = checkConnectivity(mContext) == ConnectivityMode.WIFI ||
            checkConnectivity(mContext) == ConnectivityMode.MOBILE

}