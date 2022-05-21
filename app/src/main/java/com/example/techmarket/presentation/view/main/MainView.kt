package com.example.techmarket.presentation.view.main

import android.content.Intent
import com.arellomobile.mvp.MvpView
import com.arellomobile.mvp.viewstate.strategy.AddToEndStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType
import com.example.techmarket.data.entities.Category
import com.example.techmarket.data.entities.Item
import com.example.techmarket.data.entities.Promotion

@StateStrategyType(AddToEndStrategy::class)
interface MainView : MvpView {
    fun loadItems(list: List<Item>)
    fun loadPromotions(list: List<Promotion>)
    fun loadCategories(list: List<Category>)
    fun startActivity(intent: Intent)
}