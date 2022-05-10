package com.example.techmarket.presentation.presenter

import android.content.Intent
import android.net.Uri
import android.util.Log
import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.example.techmarket.data.Item
import com.example.techmarket.data.Promotion
import com.example.techmarket.data.repository.RepositoryImpl
import com.example.techmarket.presentation.view.main.MainView
import com.github.terrakok.cicerone.Router
import com.google.firebase.database.ktx.getValue
import javax.inject.Inject

@InjectViewState
class MainPresenter : MvpPresenter<MainView>() {

    @Inject
    lateinit var repository: RepositoryImpl

    @Inject
    lateinit var router: Router

    fun getItemsFromServer() {
        repository.getMenuItems().addOnSuccessListener {
            it.getValue<HashMap<String,Item>>()?.let {
                viewState.loadItems(it.values.toList())
            }
        }
    }

    fun loadPromotions() {
        repository.getPromotions().addOnSuccessListener {
            it.getValue<List<Promotion>>()?.let { viewState.loadPromotions(it) }
        }
    }

    fun loadCategories() {
        viewState.loadCategories(repository.getCategories())
    }

    fun navigateToWeb(url: String) {
        val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
        viewState.startActivity(browserIntent)
    }
}
