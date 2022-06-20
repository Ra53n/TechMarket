package com.example.techmarket.presentation.presenter

import android.os.Handler
import android.os.Looper
import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.example.techmarket.Screens
import com.example.techmarket.data.entities.Item
import com.example.techmarket.data.entities.User
import com.example.techmarket.data.repository.localRepository.LocalRepositoryImpl
import com.example.techmarket.presentation.view.like.LikeView
import com.github.terrakok.cicerone.Router
import javax.inject.Inject

@InjectViewState
class LikePresenter : MvpPresenter<LikeView>() {
    lateinit var compareItems : List<Item>
    @Inject
    lateinit var localRepository: LocalRepositoryImpl

    @Inject
    lateinit var router: Router

    fun loadLikedItems() {
        Thread {
            val list = localRepository.getAllLikedItems()
            compareItems = localRepository.getAllCompareItems()
            Handler(Looper.getMainLooper()).post { viewState.setData(list) }
        }.start()
    }

    fun deleteLikedItem(item: Item) {
        Thread {
            localRepository.deleteLikedItems(item)
            loadLikedItems()
        }.start()
    }

    fun addToCompare(item: Item) {
        Thread {
            localRepository.addItemToCompare(item)
        }.start()
    }

    fun deleteFromCompare(item: Item) {
        Thread {
            localRepository.deleteCompareItem(item)
        }.start()
    }

    fun onItemClick(item: Item) {
        router.navigateTo(Screens.details(item))
    }

    fun isItemContainsCompare(item: Item): Boolean {
        return compareItems.map { it.id }.contains(item.id)
    }

    fun addToCart(item: Item, user: User?, price: String?) {
        Thread {
            localRepository.addItemToCart(item, user, price)
        }.start()
        viewState.itemAddedToCart()
    }
}