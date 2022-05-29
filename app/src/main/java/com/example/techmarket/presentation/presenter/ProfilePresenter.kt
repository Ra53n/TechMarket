package com.example.techmarket.presentation.presenter

import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.example.techmarket.Screens.compare
import com.example.techmarket.presentation.view.profile.ProfileView
import com.github.terrakok.cicerone.Router
import javax.inject.Inject

@InjectViewState
class ProfilePresenter : MvpPresenter<ProfileView>() {
    @Inject
    lateinit var router: Router

    fun onCompareClick() {
        router.navigateTo(compare())
    }
}