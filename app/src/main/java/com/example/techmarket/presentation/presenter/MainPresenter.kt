package com.example.techmarket.presentation.presenter

import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.example.techmarket.data.repository.RepositoryImpl
import com.example.techmarket.presentation.view.main.MainView
import javax.inject.Inject

@InjectViewState
class MainPresenter : MvpPresenter<MainView>() {
    @Inject
    lateinit var repository: RepositoryImpl

    fun getItemsFromServer() {
        viewState.renderData(repository.getMenuItems())
    }
}