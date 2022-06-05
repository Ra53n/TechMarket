package com.example.techmarket.presentation.view.authorization.login

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.example.techmarket.APP_SCOPE
import com.example.techmarket.databinding.LoginFragmentBinding
import com.example.techmarket.presentation.presenter.LoginPresenter
import com.example.techmarket.presentation.view.base.BaseFragment
import toothpick.Toothpick

const val LOGIN_SCOPE = "LOGIN_SCOPE"

class LoginFragment : BaseFragment(),
    LoginView {

    private var _binding: LoginFragmentBinding? = null
    private val binding get() = _binding!!

    companion object {
        fun newInstance() = LoginFragment()
    }

    @InjectPresenter
    lateinit var presenter: LoginPresenter

    @ProvidePresenter
    fun provideLoginPresenter(): LoginPresenter =
        Toothpick.openScopes(APP_SCOPE, LOGIN_SCOPE)
            .getInstance(LoginPresenter::class.java)
            .also { Toothpick.closeScope(LOGIN_SCOPE) }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = LoginFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.P)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setProgressBar(binding.loginFragmentProgressBar)
        bindClickListeners()
    }

    @RequiresApi(Build.VERSION_CODES.P)
    private fun bindClickListeners() {
        with(binding) {
            loginFragmentBtLogin.setOnClickListener {
                val email = loginFragmentEtEmail.text.toString()
                val password = loginFragmentEtPassword.text.toString()
                presenter.signIn(email, password)
            }
            loginFragmentTvRegister.setOnClickListener {
                presenter.onRegClick()
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}