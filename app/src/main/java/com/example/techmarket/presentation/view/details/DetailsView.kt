package com.example.techmarket.presentation.view.details

import com.arellomobile.mvp.MvpView
import com.arellomobile.mvp.viewstate.strategy.AddToEndStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType

@StateStrategyType(AddToEndStrategy::class)
interface DetailsView : MvpView {
    fun setContainsCompares(contains: Boolean)
}