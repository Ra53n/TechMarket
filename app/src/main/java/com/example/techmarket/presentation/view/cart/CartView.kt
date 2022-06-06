package com.example.techmarket.presentation.view.cart

import android.content.Intent
import com.arellomobile.mvp.MvpView
import com.arellomobile.mvp.viewstate.strategy.AddToEndStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType
import com.example.techmarket.data.cartDb.CartItemEntity
import com.example.techmarket.data.entities.Item

@StateStrategyType(AddToEndStrategy::class)
interface CartView : MvpView {
    fun setData(list: List<CartItemEntity>)
    fun setTotalCost(cost: Int)
    fun sendOrder(intent: Intent)
    fun showNoCartItems()
}