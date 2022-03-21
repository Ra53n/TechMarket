package com.example.techmarket.view.auth

import android.os.*
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.techmarket.databinding.AuthFragmentBinding
import com.example.techmarket.view.BaseFragment
import com.google.firebase.auth.FirebaseAuth

class AuthFragment private constructor(private var controller: Controller) : BaseFragment() {

    private var _binding: AuthFragmentBinding? = null
    private val binding get() = _binding!!

    private val mAuth: FirebaseAuth by lazy { FirebaseAuth.getInstance() }
    private val handler = Handler(Looper.getMainLooper())

    interface Controller {
        fun switchToRegClick()
        fun onLoginClick()
    }

    companion object {
        fun newInstance(controller: Controller) = AuthFragment(controller)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = AuthFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setProgressBar(binding.authFragmentProgressBar)
        bindClickListeners()
    }

    private fun bindClickListeners() {
        Thread {
            with(binding) {
                authFragmentBtLogin.setOnClickListener {
                    val email = authFragmentEtEmail.text.toString()
                    val password = authFragmentEtPassword.text.toString()
                    signIn(email, password)
                }
                authFragmentTvRegister.setOnClickListener {
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