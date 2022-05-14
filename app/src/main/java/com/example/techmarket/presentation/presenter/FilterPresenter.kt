package com.example.techmarket.presentation.presenter

import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.example.techmarket.APP_SCOPE
import com.example.techmarket.data.Category
import com.example.techmarket.data.Item
import com.example.techmarket.data.repository.RepositoryImpl
import com.example.techmarket.presentation.view.filter.FilterView
import com.google.firebase.database.ktx.getValue
import toothpick.Toothpick
import javax.inject.Inject

@InjectViewState
class FilterPresenter : MvpPresenter<FilterView>() {

    init {
        Toothpick.inject(this, Toothpick.openScope(APP_SCOPE))
    }

    @Inject
    lateinit var repository: RepositoryImpl

    fun loadByCategory(category: Category){
        repository.getMenuItems().addOnSuccessListener {
            it.getValue<HashMap<String, Item>>()?.let {
                viewState.loadItems(it.values.toList().filter { it.category == category })
            }
        }
    }
}