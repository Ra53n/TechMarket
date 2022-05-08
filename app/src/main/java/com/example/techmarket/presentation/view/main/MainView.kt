package com.example.techmarket.presentation.view.main

import com.arellomobile.mvp.MvpView
import com.arellomobile.mvp.viewstate.strategy.AddToEndStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType
import com.example.techmarket.data.Item

@StateStrategyType(AddToEndStrategy::class)
interface MainView : MvpView {
    fun renderData(list: List<Item>)
}