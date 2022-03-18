package com.example.techmarket.view.auth

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.techmarket.databinding.AuthFragmentBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.ktx.Firebase

class AuthFragment : Fragment() {

    private var _binding: AuthFragmentBinding? = null
    private val binding get() = _binding!!

    private lateinit var mAuth: FirebaseAuth

    companion object {
        fun newInstance() = AuthFragment()
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
        with(binding) {
            authFragmentBtLogin.setOnClickListener {
                val email = authFragmentEtLoginName.text.toString()
                val password = authFragmentEtLoginPassword.text.toString()
                signIn(email,password)
            }
        }
        mAuth = FirebaseAuth.getInstance()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private fun signIn(email: String, password: String) {
        mAuth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(requireActivity()) { task ->
                if(task.isSuccessful){
                    Toast.makeText(context,"Success",Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(context,"Fail",Toast.LENGTH_SHORT).show()
                }
            }
    }

}