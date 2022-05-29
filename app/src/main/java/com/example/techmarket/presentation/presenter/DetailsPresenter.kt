package com.example.techmarket.presentation.presenter

import android.os.Handler
import android.os.Looper
import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.example.techmarket.data.entities.Item
import com.example.techmarket.data.repository.localRepository.LocalRepositoryImpl
import com.example.techmarket.presentation.view.details.DetailsView
import javax.inject.Inject

@InjectViewState
class DetailsPresenter : MvpPresenter<DetailsView>() {
    @Inject
    lateinit var localRepository: LocalRepositoryImpl

    fun likeItem(item: Item) {
        Thread {
            localRepository.likeItem(item)
        }.start()
    }

    fun setCheckCompare(item: Item) {
        Thread {
            val contains = localRepository.isItemContainsCompares(item)
            Handler(Looper.getMainLooper()).post { viewState.setContainsCompares(contains) }
        }.start()
    }

    fun deleteFromCompare(item: Item){
        Thread {
        localRepository.deleteCompareItem(item)
        }.start()
    }

    fun addToCart(item: Item) {
        Thread {
            localRepository.addItemToCart(item)
        }.start()
    }

    fun addToCompare(item: Item) {
        Thread {
            localRepository.addItemToCompare(item)
        }.start()
    }
}