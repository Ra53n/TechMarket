package com.example.techmarket.presentation.presenter

import android.content.Intent
import android.net.Uri
import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.example.techmarket.Screens.details
import com.example.techmarket.data.entities.Item
import com.example.techmarket.data.entities.Promotion
import com.example.techmarket.data.repository.RepositoryImpl
import com.example.techmarket.data.repository.localRepository.LocalRepositoryImpl
import com.example.techmarket.presentation.view.main.MainView
import com.github.terrakok.cicerone.Router
import com.google.firebase.database.ktx.getValue
import javax.inject.Inject

@InjectViewState
class MainPresenter : MvpPresenter<MainView>() {

    private lateinit var likeItems: List<Item>

    init {
        Thread {
            Thread.sleep(100)
            likeItems = localRepository.getAllLikedItems()
        }.start()
    }

    @Inject
    lateinit var repository: RepositoryImpl

    @Inject
    lateinit var router: Router

    @Inject
    lateinit var localRepository: LocalRepositoryImpl

    fun getItemsFromServer() {
        repository.getMenuItems().addOnSuccessListener {
            it.getValue<HashMap<String, Item>>()?.let {
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

    fun likeItem(item: Item) {
        Thread {
            localRepository.likeItem(item)
        }.start()
    }

    fun addToCart(item: Item) {
        Thread {
            localRepository.addItemToCart(item, null, null)
        }.start()
    }

    fun onItemClick(item: Item) {
        router.navigateTo(details(item))
    }

    fun isItemLiked(item: Item): Boolean {
        return likeItems.map { it.id }.contains(item.id)
    }
}
