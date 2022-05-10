package com.example.techmarket.presentation.view.addItem

import com.arellomobile.mvp.MvpView
import com.arellomobile.mvp.viewstate.strategy.AddToEndStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType

@StateStrategyType(AddToEndStrategy::class)
interface AddItemView : MvpView {
    fun addFieldsFromList(list: List<String>)
}