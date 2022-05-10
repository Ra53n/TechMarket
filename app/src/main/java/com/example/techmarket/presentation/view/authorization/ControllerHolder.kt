package com.example.techmarket.presentation.view.authorization

import com.example.techmarket.APP_SCOPE
import com.example.techmarket.Screens.login
import com.example.techmarket.Screens.profile
import com.example.techmarket.Screens.registration
import com.example.techmarket.presentation.view.authorization.login.LoginFragment
import com.example.techmarket.presentation.view.authorization.registration.RegistrationFragment
import com.github.terrakok.cicerone.Router
import toothpick.Toothpick
import javax.inject.Inject

class ControllerHolder @Inject constructor() : RegistrationFragment.Controller,
    LoginFragment.Controller {

    init {
        Toothpick.inject(this, Toothpick.openScope(APP_SCOPE))
    }

    @Inject
    lateinit var router: Router

    override fun switchToLogClick() {
        router.replaceScreen(login())
    }

    override fun onRegistrationClick() {
        openProfileFragment()
    }

    override fun switchToRegClick() {
        router.replaceScreen(registration())
    }

    override fun onLoginClick() {
        openProfileFragment()
    }

    private fun openProfileFragment() {
        router.replaceScreen(profile())
    }
}