package com.example.techmarket.presentation.presenter

import android.os.Handler
import android.os.Looper
import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.example.techmarket.BuildConfig
import com.example.techmarket.data.entities.Item
import com.example.techmarket.data.entities.User
import com.example.techmarket.data.mappers.EntityItemsMapper
import com.example.techmarket.data.repository.localRepository.LocalRepositoryImpl
import com.example.techmarket.presentation.utils.GmailSender
import com.example.techmarket.presentation.view.cart.CartView
import javax.inject.Inject


@InjectViewState
class CartPresenter : MvpPresenter<CartView>() {
    @Inject
    lateinit var localRepository: LocalRepositoryImpl

    @Inject
    lateinit var mapper : EntityItemsMapper

    fun loadItems() {
        Thread {
            val list = localRepository.getAllCartItems()
            var result = 0
            for (item in list) {
                result += item.count * item.price
            }
            Handler(Looper.getMainLooper()).post {
                viewState.setData(list)
                viewState.setTotalCost(result)
            }
        }.start()
    }

    fun deleteLikedItem(item: Item) {
        Thread {
            localRepository.deleteItemFromCart(item)
            loadItems()
        }.start()
    }

    fun changeItemCount(item: Item, increase: Boolean) {
        Thread {
            if (increase) {
                localRepository.updateCartItem(item.apply { this.count = ++this.count }, null, null)
            } else if (item.count > 1) {
                localRepository.updateCartItem(item.apply { this.count = --this.count }, null, null)
            }
            loadItems()
        }.start()
    }

    fun sendOrder() {
        val sender = GmailSender("luckyfrost2301@gmail.com", BuildConfig.EMAIL_PASSWORD)
        Thread {
            val order = mutableListOf<String>()
            order.addAll(
                localRepository.getAllCartItems().map { "${it.description} - ${it.count}" })
            sender.sendMail(
                "Заказ",
                order.toString(),
                "luckyfrost2301@gmail.com",
                "nikkon2001@mail.ru"
            )
        }.start()
    }

    fun changeSeller(item: Item, seller: User, price: String) {
        Thread {
            localRepository.updateCartItem(item, seller, price)
            loadItems()
        }.start()
    }
}