package com.example.techmarket.presentation.view.compare

import com.arellomobile.mvp.MvpView
import com.arellomobile.mvp.viewstate.strategy.AddToEndStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType
import com.example.techmarket.data.entities.Item

@StateStrategyType(AddToEndStrategy::class)
interface CompareView : MvpView {
    fun generateViews(list: List<Item>)
    fun itemAddedToCart()
}