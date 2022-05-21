package com.example.techmarket.presentation.presenter

import android.os.Handler
import android.os.Looper
import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.example.techmarket.data.entities.Item
import com.example.techmarket.data.repository.localRepository.LocalRepositoryImpl
import com.example.techmarket.presentation.view.like.LikeView
import javax.inject.Inject

@InjectViewState
class LikePresenter : MvpPresenter<LikeView>() {
    @Inject
    lateinit var localRepository: LocalRepositoryImpl

    fun loadLikedItems() {
        Thread {
            val list = localRepository.getAllLikedItems()
            Handler(Looper.getMainLooper()).post { viewState.setData(list) }
        }.start()
    }

    fun deleteLikedItem(item: Item) {
        Thread {
            localRepository.deleteLikedItems(item)
            loadLikedItems()
        }.start()
    }
}