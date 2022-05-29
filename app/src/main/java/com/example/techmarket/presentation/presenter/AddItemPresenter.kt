package com.example.techmarket.presentation.presenter

import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.example.techmarket.data.categoryRepository.CategoryRepositoryImpl
import com.example.techmarket.data.entities.Category
import com.example.techmarket.presentation.view.addItem.AddItemView
import com.google.firebase.database.ktx.getValue

@InjectViewState
class AddItemPresenter : MvpPresenter<AddItemView>() {

    private val repository = CategoryRepositoryImpl()

    fun getFields(category: Category) {
        repository.getFields(category).addOnSuccessListener {
            it.getValue<List<String>>()?.let { viewState.addFieldsFromList(it) }
        }
    }
}