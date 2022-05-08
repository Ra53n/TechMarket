package com.example.techmarket

import android.app.Application
import com.example.techmarket.di.AppModule
import toothpick.Toothpick

const val APP_SCOPE = "APP"

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        Toothpick.openScope(APP_SCOPE)
            .installModules(AppModule())
    }
}