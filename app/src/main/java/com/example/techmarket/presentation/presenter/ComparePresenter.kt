package com.example.techmarket.presentation.presenter

import android.os.Handler
import android.os.Looper
import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.example.techmarket.data.entities.Item
import com.example.techmarket.data.repository.localRepository.LocalRepositoryImpl
import com.example.techmarket.presentation.view.compare.CompareView
import javax.inject.Inject

@InjectViewState
class ComparePresenter : MvpPresenter<CompareView>() {
    @Inject
    lateinit var localRepository: LocalRepositoryImpl

    fun loadItems() {
        Thread {
            val list = localRepository.getAllCompareItems()
            Handler(Looper.getMainLooper()).post { viewState.generateViews(list) }
        }.start()
    }

    fun deleteItem(item: Item){
        Thread {
            localRepository.deleteCompareItem(item)
        }.start()
        loadItems()
    }

    fun addToCompare(item: Item) {
        Thread {
            localRepository.addItemToCompare(item)
        }.start()
    }

    fun deleteFromCompare(item: Item){
        Thread {
            localRepository.deleteCompareItem(item)
        }.start()
    }
}