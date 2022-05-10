package com.example.techmarket.presentation.view.authorization.registration

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.techmarket.databinding.RegFragmentBinding
import com.example.techmarket.presentation.view.authorization.validateForm
import com.example.techmarket.presentation.view.base.BaseFragment
import com.google.firebase.auth.FirebaseAuth

class RegistrationFragment private constructor(private var controller: Controller) :
    BaseFragment() {
    private var _binding: RegFragmentBinding? = null
    private val binding get() = _binding!!

    private val mAuth: FirebaseAuth by lazy { FirebaseAuth.getInstance() }
    private val handler = Handler(Looper.getMainLooper())

    interface Controller {
        fun switchToLogClick()
        fun onRegistrationClick()
    }

    companion object {
        fun newInstance(controller: Controller) = RegistrationFragment(controller)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = RegFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bindClickListeners()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private fun bindClickListeners() {
        Thread {
            bindRegistrationBtClickListener()
            setProgressBar(binding.regFragmentProgressBar)
            bindSwitchToLoginBtClickListener()
        }.start()
    }

    private fun bindRegistrationBtClickListener() {
        with(binding) {
            regFragmentBtRegistration.setOnClickListener {
                val email = regFragmentEtEmail.text.toString()
                val password = regFragmentEtPassword.text.toString()
                if (validateForm(email, password)) {
                    createAccount(email, password)
                } else {
                    Toast.makeText(context, "Введите корректные данные!", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun bindSwitchToLoginBtClickListener() {
        with(binding) {
            regFragmentTvLogin.setOnClickListener {
                showProgressBar()
                handler.postDelayed({
                    controller.switchToLogClick()
                    hideProgressBar()
                }, 1000)
            }
        }
    }


    private fun createAccount(email: String, password: String) {
        showProgressBar()
        mAuth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(requireActivity()) { task ->
                if (task.isSuccessful) {
                    controller.onRegistrationClick()
                } else {
                    Toast.makeText(context, "Fail", Toast.LENGTH_SHORT).show()
                }
                hideProgressBar()
            }
    }

}