package com.example.techmarket.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toolbar
import androidx.annotation.MainThread
import com.example.techmarket.R
import com.example.techmarket.view.auth.AuthFragment
import com.example.techmarket.view.registration.RegistrationFragment

class MainActivity : AppCompatActivity(), RegistrationFragment.Controller, AuthFragment.Controller {

    private val authFragment = AuthFragment.newInstance(this)
    private val registrationFragment = RegistrationFragment.newInstance(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .add(R.id.container, registrationFragment)
                .commitNow()
        }
    }

    override fun onHasAnAccountClick() {
        supportFragmentManager.beginTransaction()
            .replace(R.id.container, authFragment).commit()
    }

    override fun onRegistrationClick() {
        supportFragmentManager.beginTransaction()
            .replace(R.id.container, registrationFragment).commit()
    }
}