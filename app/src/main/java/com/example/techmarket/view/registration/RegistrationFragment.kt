package com.example.techmarket.view.registration

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.techmarket.databinding.RegFragmentBinding
import com.example.techmarket.view.BaseFragment
import com.google.firebase.auth.FirebaseAuth

class RegistrationFragment private constructor(private var controller: Controller) :
    BaseFragment() {
    private var _binding: RegFragmentBinding? = null
    private val binding get() = _binding!!

    private val mAuth: FirebaseAuth by lazy { FirebaseAuth.getInstance() }
    private val handler = Handler(Looper.getMainLooper())

    interface Controller {
        fun onHasAnAccountClick()
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
        Thread {
            with(binding) {
                regFragmentBtRegistration.setOnClickListener {
                    val email = regFragmentEtEmail.text.toString()
                    val password = regFragmentEtPassword.text.toString()
                    createAccount(email, password)
                }
                setProgressBar(regFragmentProgressBar)
                regFragmentTvLogin.setOnClickListener {
                    showProgressBar()
                    handler.postDelayed({
                        controller.onHasAnAccountClick()
                        hideProgressBar()
                    }, 1000)
                }
            }
        }.start()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private fun createAccount(email: String, password: String) {
        mAuth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(requireActivity()) { task ->
                if (task.isSuccessful) {
                    Toast.makeText(context, "Success", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(context, "Fail", Toast.LENGTH_SHORT).show()
                }
            }
    }

}