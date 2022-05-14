package com.example.techmarket.di

import android.content.Context
import com.example.techmarket.App
import com.example.techmarket.data.repository.RepositoryImpl
import com.github.terrakok.cicerone.Cicerone
import com.github.terrakok.cicerone.NavigatorHolder
import com.github.terrakok.cicerone.Router
import toothpick.config.Module

class AppModule(app: App) : Module() {
    init {
        val cicerone = Cicerone.create()
        bind(Router::class.java).toInstance(cicerone.router)
        bind(NavigatorHolder::class.java).toInstance(cicerone.getNavigatorHolder())
        bind(RepositoryImpl::class.java).toInstance(RepositoryImpl())
        bind(Context::class.java).toInstance(app.applicationContext)
    }
}