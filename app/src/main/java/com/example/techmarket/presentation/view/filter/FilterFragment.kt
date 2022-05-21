package com.example.techmarket.presentation.view.filter

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.arellomobile.mvp.presenter.InjectPresenter
import com.example.techmarket.data.entities.Category
import com.example.techmarket.data.entities.Item
import com.example.techmarket.databinding.FilterFragmentBinding
import com.example.techmarket.presentation.presenter.FilterPresenter
import com.example.techmarket.presentation.view.adapters.FilterAdapter
import com.example.techmarket.presentation.view.base.BaseFragment

class FilterFragment(private val category: Category) : BaseFragment(), FilterView {
    private var _binding: FilterFragmentBinding? = null
    private val binding get() = _binding!!

    private lateinit var adapter: FilterAdapter
    private val controller = object : FilterAdapter.Controller{
        override fun likeItem(item: Item) {
            presenter.likeItem(item)
        }

    }

    @InjectPresenter
    lateinit var presenter: FilterPresenter



    companion object {
        fun newInstance(category: Category) = FilterFragment(category)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FilterFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapter = FilterAdapter(controller)
        binding.filterFragmentRecyclerView.adapter = adapter
        binding.filterFragmentRecyclerView.layoutManager = LinearLayoutManager(context)
        presenter.loadByCategory(category)
    }

    override fun loadItems(list: List<Item>) {
        adapter.setItems(list)
    }
}