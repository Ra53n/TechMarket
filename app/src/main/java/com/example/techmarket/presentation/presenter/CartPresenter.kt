package com.example.techmarket.presentation.presenter

import android.content.Context
import android.os.Handler
import android.os.Looper
import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.example.techmarket.App
import com.example.techmarket.BuildConfig
import com.example.techmarket.R
import com.example.techmarket.Screens
import com.example.techmarket.data.entities.Item
import com.example.techmarket.data.entities.User
import com.example.techmarket.data.mappers.EntityItemsMapper
import com.example.techmarket.data.repository.localRepository.LocalRepositoryImpl
import com.example.techmarket.presentation.utils.GmailSender
import com.example.techmarket.presentation.view.cart.CartView
import com.github.terrakok.cicerone.Router
import javax.inject.Inject


@InjectViewState
class CartPresenter : MvpPresenter<CartView>() {

    @Inject
    lateinit var localRepository: LocalRepositoryImpl

    @Inject
    lateinit var mapper: EntityItemsMapper

    @Inject
    lateinit var context: Context

    @Inject
    lateinit var router: Router

    fun loadItems() {
        Thread {
            val list = localRepository.getAllCartItems()
            var result = 0
            for (item in list) {
                result += item.count * item.price
            }
            Handler(Looper.getMainLooper()).post {
                if (list.isEmpty()) {
                    viewState.showNoCartItems()
                } else {
                    viewState.setData(list)
                    viewState.setTotalCost(result)
                }
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
        App.currentUser?.let {
            if (it.address.isNotEmpty()) {
                val sender = GmailSender(SENDER, BuildConfig.EMAIL_PASSWORD)
                Thread {
                    val cartItems = localRepository.getAllCartItems()
                    for (cartItem in cartItems) {
                        if (cartItem.selectedSeller == null) Thread.currentThread().interrupt()
                    }
                    val mapOfSellerAndItems = mutableMapOf<String, MutableList<Item>>()
                    for (item in cartItems) {
                        if (!mapOfSellerAndItems.containsKey(item.selectedSeller!!.email)) {
                            mapOfSellerAndItems[item.selectedSeller!!.email] =
                                mutableListOf(mapper.convertCartItemToItem(item))
                        } else {
                            mapOfSellerAndItems[item.selectedSeller!!.email]!!.add(
                                mapper.convertCartItemToItem(
                                    item
                                )
                            )
                        }
                    }
                    for (entry in mapOfSellerAndItems.entries) {
                        val order = mutableListOf<String>()
                        order.addAll(
                            entry.value.map { "${it.description} - ${it.count}" })
                        sender.sendMail(
                            "Заказ",
                            context.resources.getString(
                                R.string.order_template,
                                order.toString(),
                                it.email,
                                it.address
                            ),
                            SENDER,
                            entry.key
                        )
                    }
                }.start()
            } else {
                viewState.showAddressErrorToast()
            }
        }
    }

    fun changeSeller(item: Item, seller: User, price: String) {
        Thread {
            localRepository.updateCartItem(item, seller, price)
            loadItems()
        }.start()
    }

    fun onItemClick(item: Item) {
        router.navigateTo(Screens.details(item))
    }

    companion object {
        private const val RECIPIENT = "riki.kike@mail.ru"
        private const val SENDER = "nikkon2001@mail.ru"
    }
}