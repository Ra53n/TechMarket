package com.example.techmarket.view.registration

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.techmarket.databinding.AuthFragmentBinding
import com.example.techmarket.databinding.RegFragmentBinding
import com.example.techmarket.view.auth.AuthFragment
import com.google.firebase.auth.FirebaseAuth

class RegistrationFragment : Fragment() {
    private var _binding: RegFragmentBinding? = null
    private val binding get() = _binding!!

    private lateinit var mAuth: FirebaseAuth

    companion object {
        fun newInstance() = RegistrationFragment()
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
        with(binding) {
            regFragmentBtLogin.setOnClickListener {
                val email = regFragmentEtLoginName.text.toString()
                val password = regFragmentEtLoginPassword.text.toString()
                createAccount(email, password)
            }
        }
        mAuth = FirebaseAuth.getInstance()
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