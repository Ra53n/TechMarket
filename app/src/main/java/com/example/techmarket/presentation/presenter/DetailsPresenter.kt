package com.example.techmarket.presentation.presenter

import android.os.Handler
import android.os.Looper
import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.example.techmarket.App
import com.example.techmarket.Screens
import com.example.techmarket.data.entities.Item
import com.example.techmarket.data.entities.User
import com.example.techmarket.data.entities.UserPricePair
import com.example.techmarket.data.repository.RepositoryImpl
import com.example.techmarket.data.repository.localRepository.LocalRepositoryImpl
import com.example.techmarket.presentation.view.details.DetailsView
import com.github.terrakok.cicerone.Router
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import javax.inject.Inject

@InjectViewState
class DetailsPresenter : MvpPresenter<DetailsView>() {
    @Inject
    lateinit var localRepository: LocalRepositoryImpl

    @Inject
    lateinit var repository: RepositoryImpl

    @Inject
    lateinit var router: Router

    fun likeItem(item: Item) {
        Thread {
            localRepository.likeItem(item)
        }.start()
    }

    fun setCheckCompare(item: Item) {
        Thread {
            val contains = localRepository.isItemContainsCompares(item)
            Handler(Looper.getMainLooper()).post { viewState.setContainsCompares(contains) }
        }.start()
    }

    fun deleteFromCompare(item: Item) {
        Thread {
            localRepository.deleteCompareItem(item)
        }.start()
    }

    fun addToCart(item: Item, user: User, price: String) {
        Thread {
            localRepository.addItemToCart(item, user, price)
        }.start()
    }

    fun addToCompare(item: Item) {
        Thread {
            localRepository.addItemToCompare(item)
        }.start()
    }

    fun addSellerToItem(item: Item, price: String) {
        val database = Firebase.database.reference
        val newItem = item.apply {
            val map = sellers.toMutableMap()
            map.putIfAbsent(
                App.currentUser!!.id, UserPricePair(
                    App.currentUser!!,
                    price
                )
            )
            sellers = map
        }
        database.child("items").child(item.id).setValue(newItem)
        router.navigateTo(Screens.details(newItem))
    }
}