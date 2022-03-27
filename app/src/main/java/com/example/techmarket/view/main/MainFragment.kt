package com.example.techmarket.view.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.techmarket.databinding.MainFragmentBinding
import com.example.techmarket.model.AppState
import com.example.techmarket.view.BaseFragment
import com.example.techmarket.viewModel.MainViewModel

class MainFragment : BaseFragment() {

    private var _binding: MainFragmentBinding? = null
    private val binding get() = _binding!!
    private val adapter = MainFragmentAdapter()

    companion object {
        fun newInstance() = MainFragment()
    }

    private lateinit var viewModel: MainViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = MainFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = MainViewModel()
        viewModel.getData().observe(viewLifecycleOwner, { renderData(it) })
        with(binding) {
            val linearLayout = {
                LinearLayoutManager(context).apply {
                    orientation = LinearLayoutManager.HORIZONTAL
                }
            }
            mainFragmentRecyclerView.adapter = adapter
            mainFragmentRecyclerView.layoutManager = linearLayout()
        }
        viewModel.getItemsFromServer()
    }

    private fun renderData(appState: AppState) {
        when (appState) {
            is AppState.Success -> {
                adapter.setItems(appState.itemList)
            }
        }
    }

}