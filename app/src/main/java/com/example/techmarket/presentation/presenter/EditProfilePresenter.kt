package com.example.techmarket.presentation.presenter

import com.arellomobile.mvp.MvpPresenter
import com.example.techmarket.App
import com.example.techmarket.Screens
import com.example.techmarket.data.repository.RepositoryImpl
import com.example.techmarket.presentation.view.editProfile.EditProfileView
import com.github.terrakok.cicerone.Router
import javax.inject.Inject

class EditProfilePresenter : MvpPresenter<EditProfileView>() {
    @Inject
    lateinit var repository: RepositoryImpl

    @Inject
    lateinit var router: Router

    fun updateUser(newName: String) {
        App.currentUser?.let {
            App.currentUser?.name = newName
            repository.updateUser(it)
        }
        router.backTo(Screens.profile())
    }
}