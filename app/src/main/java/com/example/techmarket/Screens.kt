package com.example.techmarket

import com.example.techmarket.presentation.view.authorization.ControllerHolder
import com.example.techmarket.presentation.view.authorization.login.LoginFragment
import com.example.techmarket.presentation.view.authorization.registration.RegistrationFragment
import com.example.techmarket.presentation.view.cart.CartFragment
import com.example.techmarket.presentation.view.main.MainFragment
import com.example.techmarket.presentation.view.profile.ProfileFragment
import com.github.terrakok.cicerone.androidx.FragmentScreen

object Screens {

    private val controllerHolder = ControllerHolder()

    fun main() = FragmentScreen {
        MainFragment.newInstance()
    }

    fun cart() = FragmentScreen {
        CartFragment.newInstance()
    }

    fun profile() = FragmentScreen {
        ProfileFragment.newInstance()
    }

    fun login() = FragmentScreen {
        LoginFragment.newInstance(controllerHolder)
    }

    fun registration() = FragmentScreen {
        RegistrationFragment.newInstance(controllerHolder)
    }
}