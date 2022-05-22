package com.example.techmarket.presentation.presenter

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

    fun addToCart(item: Item) {
        Thread {
            localRepository.addItemToCart(item)
        }.start()
    }
}