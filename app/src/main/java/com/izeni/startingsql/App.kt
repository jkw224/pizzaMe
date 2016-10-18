package com.izeni.startingsql

import android.app.Application
import com.facebook.stetho.Stetho

/**
 * Created by jonny on 10/17/16.
 */
class App : Application() {

    companion object {
        lateinit var instance: Application
    }

    override fun onCreate() {
        super.onCreate()

        instance = this
        Stetho.initializeWithDefaults(this)


    }

}