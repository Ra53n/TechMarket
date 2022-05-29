package com.example.techmarket.di

import android.content.Context
import com.example.techmarket.App
import com.example.techmarket.data.cartDb.CartItemsDatabase
import com.example.techmarket.data.compareDb.CompareItemsDatabase
import com.example.techmarket.data.likesDb.LikedItemsDatabase
import com.example.techmarket.data.repository.Repository
import com.example.techmarket.data.repository.RepositoryImpl
import com.example.techmarket.data.repository.localRepository.LocalRepository
import com.example.techmarket.data.repository.localRepository.LocalRepositoryImpl
import com.example.techmarket.di.provider.CartItemsProvider
import com.example.techmarket.di.provider.CompareItemsProvider
import com.example.techmarket.di.provider.LikedItemsProvider
import com.github.terrakok.cicerone.Cicerone
import com.github.terrakok.cicerone.NavigatorHolder
import com.github.terrakok.cicerone.Router
import toothpick.config.Module

class AppModule(app: App) : Module() {
    init {
        val cicerone = Cicerone.create()
        bind(Router::class.java).toInstance(cicerone.router)
        bind(NavigatorHolder::class.java).toInstance(cicerone.getNavigatorHolder())
        bind(Repository::class.java).toInstance(RepositoryImpl())
        bind(LocalRepository::class.java).to(LocalRepositoryImpl::class.java)
        bind(Context::class.java).toInstance(app.applicationContext)

        bind(LikedItemsDatabase::class.java).toProvider(LikedItemsProvider::class.java)
        bind(CartItemsDatabase::class.java).toProvider(CartItemsProvider::class.java)
        bind(CompareItemsDatabase::class.java).toProvider(CompareItemsProvider::class.java)
    }
}