package com.myapp.newsmvvmappdemo.application

import android.content.Context
import android.support.multidex.MultiDex
import android.support.multidex.MultiDexApplication
import dagger.hilt.android.HiltAndroidApp


@HiltAndroidApp
class GlobalApplication : MultiDexApplication() {

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        MultiDex.install(this)
    }
}