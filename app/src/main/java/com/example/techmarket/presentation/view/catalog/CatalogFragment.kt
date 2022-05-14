package com.example.techmarket.presentation.view.catalog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Adapter
import com.arellomobile.mvp.presenter.InjectPresenter
import com.example.techmarket.databinding.CatalogFragmentBinding
import com.example.techmarket.presentation.presenter.CatalogPresenter
import com.example.techmarket.presentation.view.base.BaseFragment

class CatalogFragment : BaseFragment(), CatalogView {
    private var _binding: CatalogFragmentBinding? = null
    private val binding get() = _binding!!

    companion object {
        fun newInstance() = CatalogFragment()
    }

    @InjectPresenter
    lateinit var presenter: CatalogPresenter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = CatalogFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bindAdapters()
    }

    private fun bindAdapters() {
        val adapterHolder = CatalogClickListenerHolder(requireContext())
        binding.buttonPhoneCategory.setOnClickListener(adapterHolder.phonesClickListener)
        binding.buttonComputerCategory.setOnClickListener (adapterHolder.computersClickListener)
        binding.buttonAppliancesCategory.setOnClickListener(adapterHolder.appliancesClickListener)
        binding.buttonOfficeCategory.setOnClickListener(adapterHolder.officeClickListener)
    }
}