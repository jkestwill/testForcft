package com.jkestwill.test

import android.app.Application
import com.jkestwill.test.di.AppComponent
import com.jkestwill.test.di.DaggerAppComponent


class App : Application() {
    lateinit var appComponent: AppComponent

    override fun onCreate() {
        super.onCreate()
        appComponent = DaggerAppComponent.builder().context(applicationContext).build()
    }
}