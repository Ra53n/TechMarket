package com.example.techmarket.presentation.view.filter

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import com.arellomobile.mvp.presenter.InjectPresenter
import com.example.techmarket.data.entities.Category
import com.example.techmarket.data.entities.Item
import com.example.techmarket.databinding.FilterFragmentBinding
import com.example.techmarket.presentation.presenter.FilterPresenter
import com.example.techmarket.presentation.view.adapters.FilterAdapter
import com.example.techmarket.presentation.view.base.BaseFragment

class FilterFragment(
    private val category: Category,
    private val characteristic: Map<String, String> = emptyMap()
) : BaseFragment(), FilterView {
    private var _binding: FilterFragmentBinding? = null
    private val binding get() = _binding!!

    private lateinit var adapter: FilterAdapter
    private val controller = object : FilterAdapter.Controller {
        override fun likeItem(item: Item) {
            presenter.likeItem(item)
        }

        override fun deleteItemFromCompare(item: Item) {
            presenter.deleteFromCompare(item)
        }

        override fun addItemToCompare(item: Item) {
            presenter.addToCompare(item)
        }

        override fun isItemContainsCompare(item: Item): Boolean {
            return presenter.isItemContainsCompare(item)
        }

        override fun isItemLiked(item: Item): Boolean {
            return presenter.isItemLiked(item)
        }

        override fun onItemClick(item: Item) {
            presenter.onItemClick(item)
        }

    }

    @InjectPresenter
    lateinit var presenter: FilterPresenter


    companion object {
        fun newInstance(category: Category, characteristics: Map<String, String>) =
            FilterFragment(category, characteristics)
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
        presenter.loadItems(category, characteristic)
        binding.filterFragmentSearchView.setOnQueryTextListener(object :
            SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                presenter.loadByQuery(binding.filterFragmentSearchView.query.toString(), category)
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return true
            }

        })
        binding.filterFragmentPane.getFragment<FilterSlidingPaneFragment>().presenter.setCategory(
            category
        )
    }

    override fun loadItems(list: List<Item>) {
        adapter.setItems(list)
    }

    override fun showError(error: String) {
        Toast.makeText(context, error, Toast.LENGTH_SHORT).show()
    }
}