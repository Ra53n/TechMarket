package com.example.techmarket.presentation.presenter

import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.example.techmarket.APP_SCOPE
import com.example.techmarket.Screens
import com.example.techmarket.data.entities.Category
import com.example.techmarket.data.entities.Item
import com.example.techmarket.data.repository.RemoteRepositoryImpl
import com.example.techmarket.data.repository.localRepository.LocalRepositoryImpl
import com.example.techmarket.presentation.view.filter.FilterView
import com.github.terrakok.cicerone.Router
import com.google.firebase.database.ktx.getValue
import toothpick.Toothpick
import javax.inject.Inject

@InjectViewState
class FilterPresenter : MvpPresenter<FilterView>() {
    private lateinit var compareItems: List<Item>
    private lateinit var likedItems: List<Item>

    init {
        Toothpick.inject(this, Toothpick.openScope(APP_SCOPE))
        Thread {
            compareItems = localRepository.getAllCompareItems()
            likedItems = localRepository.getAllLikedItems()
        }.start()
    }

    @Inject
    lateinit var repository: RemoteRepositoryImpl

    @Inject
    lateinit var localRepository: LocalRepositoryImpl

    @Inject
    lateinit var router: Router

    fun loadItems(category: Category, characteristics: Map<String, String>) {
        repository.getMenuItems().addOnSuccessListener {
            it.getValue<HashMap<String, Item>>()?.let {
                viewState.loadItems(
                    it.values.toList().filter { isItemFits(it, category, characteristics) })
            }
        }
    }

    private fun isItemFits(item: Item, category: Category, characteristics: Map<String, String>): Boolean {
        var fits = item.category == category
        if (fits && characteristics.isNotEmpty()) {
            fits = matchesMap(item.characteristic, characteristics)
        }
        return fits
    }

    private fun matchesMap(map1: Map<String, String>, reference: Map<String, String>): Boolean {
        return reference.all { (k, v) -> map1[k].equals(v, ignoreCase = true) }
    }

    fun likeItem(item: Item) {
        Thread {
            val contains = localRepository.isItemLiked(item)
            if (contains) {
                localRepository.deleteLikedItems(item)
            } else {
                localRepository.likeItem(item)
            }
            likedItems = localRepository.getAllLikedItems()
        }.start()
    }

    fun addToCompare(item: Item) {
        Thread {
            localRepository.addItemToCompare(item)
        }.start()
    }

    fun deleteFromCompare(item: Item) {
        Thread {
            localRepository.deleteCompareItem(item)
        }.start()
    }

    fun isItemContainsCompare(item: Item): Boolean {
        return compareItems.map { it.id }.contains(item.id)
    }

    fun isItemLiked(item: Item): Boolean {
        Thread.sleep(100)
        return likedItems.map { it.id }.contains(item.id)
    }

    fun loadByQuery(query: String, category: Category) {
        repository.getMenuItems().addOnSuccessListener {
            it.getValue<HashMap<String, Item>>()?.let {
                if (it.isEmpty()) {
                    viewState.showError("Ничего не найдено!")
                }
                viewState.loadItems(
                    it.values.toList()
                        .filter {
                            it.category == category && it.description.contains(
                                query,
                                ignoreCase = true
                            )
                        })
            }
        }
    }

    fun onItemClick(item: Item) {
        router.navigateTo(Screens.details(item))
    }
}