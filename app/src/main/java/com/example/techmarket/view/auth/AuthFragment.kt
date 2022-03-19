package com.example.techmarket.view.auth

import android.os.*
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.techmarket.databinding.AuthFragmentBinding
import com.example.techmarket.view.BaseFragment
import com.example.techmarket.view.registration.RegistrationFragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.ktx.Firebase
import java.util.concurrent.Executors

class AuthFragment private constructor(private var controller: Controller) : BaseFragment() {

    private var _binding: AuthFragmentBinding? = null
    private val binding get() = _binding!!

    private val mAuth: FirebaseAuth by lazy { FirebaseAuth.getInstance() }
    private val handler = Handler(Looper.getMainLooper())

    interface Controller {
        fun onRegistrationClick()
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
                            controller.onRegistrationClick()
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
                        Toast.makeText(context, "Success", Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(context, "Fail", Toast.LENGTH_SHORT).show()
                    }
                    hideProgressBar()
                }
        }
    }

}