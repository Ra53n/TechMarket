package com.example.techmarket.presentation.presenter

import android.content.Context
import android.os.Build
import android.os.Handler
import android.os.Looper
import android.widget.Toast
import androidx.annotation.RequiresApi
import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.example.techmarket.App
import com.example.techmarket.Screens
import com.example.techmarket.data.entities.User
import com.example.techmarket.data.repository.RepositoryImpl
import com.example.techmarket.presentation.view.authorization.registration.RegistrationView
import com.github.terrakok.cicerone.Router
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import javax.inject.Inject

@InjectViewState
class RegistrationPresenter : MvpPresenter<RegistrationView>() {
    @Inject
    lateinit var repository: RepositoryImpl

    @Inject
    lateinit var router: Router

    @Inject
    lateinit var context: Context


    private var database = Firebase.database.reference
    private val mAuth: FirebaseAuth by lazy { FirebaseAuth.getInstance() }
    private val handler = Handler(Looper.getMainLooper())

    @RequiresApi(Build.VERSION_CODES.P)
    fun createAccount(email: String, name: String, password: String, isSeller: Boolean) {
        viewState.showProgressBar()
        mAuth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(context.mainExecutor) { task ->
                if (task.isSuccessful) {
                    onRegistrationClick()
                    createUser(email, name, password, isSeller)
                } else {
                    Toast.makeText(context, "Fail", Toast.LENGTH_SHORT).show()
                }
                viewState.hideProgressBar()
            }
    }

    private fun createUser(email: String, name: String, password: String, isSeller: Boolean) {
        val newUser = User(email = email, password = password, name = name, seller = isSeller)
        database.child("users").child(newUser.id).setValue(newUser)
        App.currentUser = newUser
    }

    fun toLoginClick() {
        viewState.showProgressBar()
        handler.postDelayed({
            router.replaceScreen(Screens.login())
            viewState.hideProgressBar()
        }, 1000)
    }

    fun onRegistrationClick() {
        router.replaceScreen(Screens.profile())
    }
}