package com.example.techmarket.view.cart

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.techmarket.databinding.CartFragmentBinding
import com.example.techmarket.databinding.ProfileFragmentBinding
import com.example.techmarket.view.BaseFragment
import com.example.techmarket.view.profile.ProfileFragment
import com.example.techmarket.viewModel.MainViewModel

class CartFragment : BaseFragment() {
    private var _binding: CartFragmentBinding? = null
    private val binding get() = _binding!!

    companion object {
        fun newInstance() = ProfileFragment()
    }

    private lateinit var viewModel: MainViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = CartFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }
}