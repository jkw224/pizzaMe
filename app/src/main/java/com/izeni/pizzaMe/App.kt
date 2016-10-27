package com.izeni.pizzaMe

import android.app.Application
import com.facebook.stetho.Stetho
import com.jakewharton.threetenabp.AndroidThreeTen

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
        AndroidThreeTen.init(this)
        Stetho.initializeWithDefaults(this)

    }

}