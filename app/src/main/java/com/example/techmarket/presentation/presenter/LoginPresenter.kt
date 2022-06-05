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
import com.example.techmarket.presentation.view.authorization.login.LoginView
import com.github.terrakok.cicerone.Router
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.ktx.getValue
import javax.inject.Inject

@InjectViewState
class LoginPresenter : MvpPresenter<LoginView>() {
    @Inject
    lateinit var router: Router

    @Inject
    lateinit var context: Context

    @Inject
    lateinit var repository: RepositoryImpl

    private val mAuth: FirebaseAuth by lazy { FirebaseAuth.getInstance() }
    private val handler = Handler(Looper.getMainLooper())

    @RequiresApi(Build.VERSION_CODES.P)
    fun signIn(email: String, password: String) {
        handler.post {
            viewState.showProgressBar()
            mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(context.mainExecutor) { task ->
                    if (task.isSuccessful) {
                        repository.getUsers().addOnSuccessListener {
                            it.getValue<Map<String, User>>()
                                ?.let { list ->
                                    val filteredList =
                                        list.values.filter { it.email == email && it.password == password }
                                    if (filteredList.size == 1) {
                                        App.currentUser = filteredList.first()
                                    }
                                }
                        }
                        handler.postDelayed({ router.navigateTo(Screens.profile()) }, 300)

                    } else {
                        Toast.makeText(context, "Fail", Toast.LENGTH_SHORT).show()
                    }
                    viewState.hideProgressBar()
                }
        }
    }

    fun onRegClick() {
        viewState.showProgressBar()
        handler.postDelayed({
            router.navigateTo(Screens.registration())
            viewState.hideProgressBar()
        }, 1000)
    }
}