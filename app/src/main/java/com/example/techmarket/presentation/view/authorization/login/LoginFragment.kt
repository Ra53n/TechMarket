package com.example.techmarket.presentation.view.authorization.login

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.techmarket.databinding.LoginFragmentBinding
import com.example.techmarket.presentation.view.base.BaseFragment
import com.google.firebase.auth.FirebaseAuth

class LoginFragment private constructor(private var controller: Controller) : BaseFragment() {

    private var _binding: LoginFragmentBinding? = null
    private val binding get() = _binding!!

    private val mAuth: FirebaseAuth by lazy { FirebaseAuth.getInstance() }
    private val handler = Handler(Looper.getMainLooper())

    interface Controller {
        fun switchToRegClick()
        fun onLoginClick()
    }

    companion object {
        fun newInstance(controller: Controller) = LoginFragment(controller)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = LoginFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setProgressBar(binding.loginFragmentProgressBar)
        bindClickListeners()
    }

    private fun bindClickListeners() {
        Thread {
            with(binding) {
                loginFragmentBtLogin.setOnClickListener {
                    val email = loginFragmentEtEmail.text.toString()
                    val password = loginFragmentEtPassword.text.toString()
                    signIn(email, password)
                }
                loginFragmentTvRegister.setOnClickListener {
                    requireActivity().runOnUiThread {
                        showProgressBar()
                        handler.postDelayed({
                            controller.switchToRegClick()
                            hideProgressBar()
                        }, 1000)
                    }
                }
            }
        }.start()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private fun signIn(email: String, password: String) {
        requireActivity().runOnUiThread {
            showProgressBar()
            mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(requireActivity()) { task ->
                    if (task.isSuccessful) {
                        controller.onLoginClick()
                    } else {
                        Toast.makeText(context, "Fail", Toast.LENGTH_SHORT).show()
                    }
                    hideProgressBar()
                }
        }
    }

}