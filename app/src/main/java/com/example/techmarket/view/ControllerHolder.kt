package com.example.techmarket.view

import androidx.fragment.app.FragmentActivity
import com.example.techmarket.R
import com.example.techmarket.view.auth.AuthFragment
import com.example.techmarket.view.profile.ProfileFragment
import com.example.techmarket.view.registration.RegistrationFragment

class ControllerHolder(private val fragment: FragmentActivity) : RegistrationFragment.Controller,
    AuthFragment.Controller {

    private val authFragment = AuthFragment.newInstance(this)
    private val registrationFragment = RegistrationFragment.newInstance(this)
    override fun switchToLogClick() {
        fragment.supportFragmentManager.beginTransaction()
            .replace(R.id.container, authFragment)
            .addToBackStack("")
            .commit()
    }

    override fun onRegistrationClick() {
        openProfileFragment()
    }

    override fun switchToRegClick() {
        fragment.supportFragmentManager.beginTransaction()
            .replace(R.id.container, registrationFragment)
            .addToBackStack("")
            .commit()
    }

    override fun onLoginClick() {
        openProfileFragment()
    }

    private fun openProfileFragment() {
        fragment.supportFragmentManager.beginTransaction()
            .replace(R.id.container, ProfileFragment.newInstance()).commit()
    }
}