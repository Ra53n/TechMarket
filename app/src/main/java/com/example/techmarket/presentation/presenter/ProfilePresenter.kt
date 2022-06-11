package com.example.techmarket.presentation.presenter

import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.example.techmarket.App
import com.example.techmarket.Screens
import com.example.techmarket.Screens.add
import com.example.techmarket.Screens.compare
import com.example.techmarket.Screens.login
import com.example.techmarket.Screens.profile
import com.example.techmarket.data.repository.RemoteRepositoryImpl
import com.example.techmarket.presentation.view.profile.ProfileView
import com.github.terrakok.cicerone.Router
import javax.inject.Inject

@InjectViewState
class ProfilePresenter : MvpPresenter<ProfileView>() {
    @Inject
    lateinit var router: Router

    @Inject
    lateinit var repository: RemoteRepositoryImpl

    fun onCompareClick() {
        router.navigateTo(compare())
    }

    fun onLoginClick() {
        router.navigateTo(login())
    }

    fun exitProfile() {
        App.currentUser = null
        router.navigateTo(profile())
    }

    fun addItem() {
        router.navigateTo(add())
    }

    fun editProfile() {
        router.navigateTo(Screens.editProfile())
    }
}