package com.example.techmarket.presentation.presenter

import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.example.techmarket.Screens
import com.example.techmarket.data.categoryRepository.CategoryRepositoryImpl
import com.example.techmarket.data.entities.Category
import com.example.techmarket.presentation.view.filter.FilterSlidingPaneView
import com.github.terrakok.cicerone.Router
import com.google.firebase.database.ktx.getValue
import javax.inject.Inject

@InjectViewState
class FilterSlidingPanePresenter : MvpPresenter<FilterSlidingPaneView>() {

    var currentCategory: Category = Category.Uncategory

    @Inject
    lateinit var router: Router

    private val repository = CategoryRepositoryImpl()

    fun openDetails(category: Category) {
        router.navigateTo(Screens.filter(category))
    }

    fun setCategory(category: Category) {
        currentCategory = category
    }

    fun addCharacteristics() {
        Thread {
            repository.getFields(currentCategory).addOnSuccessListener {
                it.getValue<List<String>>()?.let {
                    viewState.addFilterViews(it)
                }
            }
        }.start()
    }

    fun applyFilters(map: MutableMap<String, String>) {
        router.navigateTo(Screens.filter(currentCategory, map))
    }
}