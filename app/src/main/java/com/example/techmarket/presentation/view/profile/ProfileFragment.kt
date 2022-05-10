package com.example.techmarket.presentation.view.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.techmarket.databinding.ProfileFragmentBinding
import com.example.techmarket.presentation.view.authorization.ControllerHolder
import com.example.techmarket.presentation.view.base.BaseFragment

class ProfileFragment : BaseFragment(), ProfileView {
    private var _binding: ProfileFragmentBinding? = null
    private val binding get() = _binding!!

    companion object {
        fun newInstance() = ProfileFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        Object()
        _binding = ProfileFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.profileFragmentBtAuthorisation.setOnClickListener {
            ControllerHolder().switchToLogClick()
        }
    }
}