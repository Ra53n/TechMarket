package com.example.techmarket.presentation.view.authorization.registration

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.example.techmarket.APP_SCOPE
import com.example.techmarket.databinding.RegFragmentBinding
import com.example.techmarket.presentation.presenter.RegistrationPresenter
import com.example.techmarket.presentation.view.authorization.validateForm
import com.example.techmarket.presentation.view.base.BaseFragment
import toothpick.Toothpick

const val REGISTRATION_SCOPE = "REGISTRATION_SCOPE"

class RegistrationFragment :
    BaseFragment(), RegistrationView {
    private var _binding: RegFragmentBinding? = null
    private val binding get() = _binding!!

    companion object {
        fun newInstance() = RegistrationFragment()
    }

    @InjectPresenter
    lateinit var presenter: RegistrationPresenter

    @ProvidePresenter
    fun provideRegistrationPresenter(): RegistrationPresenter =
        Toothpick.openScopes(APP_SCOPE, REGISTRATION_SCOPE)
            .getInstance(RegistrationPresenter::class.java)
            .also { Toothpick.closeScope(REGISTRATION_SCOPE) }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = RegFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.P)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bindClickListeners()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    @RequiresApi(Build.VERSION_CODES.P)
    private fun bindClickListeners() {
        Thread {
            bindRegistrationBtClickListener()
            setProgressBar(binding.regFragmentProgressBar)
            bindSwitchToLoginBtClickListener()
        }.start()
    }

    @RequiresApi(Build.VERSION_CODES.P)
    private fun bindRegistrationBtClickListener() {
        with(binding) {
            regFragmentBtRegistration.setOnClickListener {
                val email = regFragmentEtEmail.text.toString()
                val password = regFragmentEtPassword.text.toString()
                val name = regFragmentEtName.text.toString()
                val isSeller = regFragmentCbIsSeller.isChecked
                if (validateForm(email, password)) {
                    presenter.createAccount(email, name, password, isSeller)
                } else {
                    Toast.makeText(context, "Введите корректные данные!", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun bindSwitchToLoginBtClickListener() {
        with(binding) {
            regFragmentTvLogin.setOnClickListener {
                presenter.toLoginClick()
            }
        }
    }

}