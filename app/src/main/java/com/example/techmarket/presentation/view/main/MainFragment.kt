package com.example.techmarket.presentation.view.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.example.techmarket.APP_SCOPE
import com.example.techmarket.data.entities.Category
import com.example.techmarket.data.entities.Item
import com.example.techmarket.data.entities.Promotion
import com.example.techmarket.databinding.MainFragmentBinding
import com.example.techmarket.presentation.presenter.MainPresenter
import com.example.techmarket.presentation.view.adapters.CategoryAdapter
import com.example.techmarket.presentation.view.adapters.MainFragmentAdapter
import com.example.techmarket.presentation.view.adapters.PromotionAdapter
import com.example.techmarket.presentation.view.base.BaseFragment
import toothpick.Toothpick

const val MAIN_SCOPE = "MAIN_SCOPE"

class MainFragment : BaseFragment(), MainView {

    private var _binding: MainFragmentBinding? = null
    private val binding get() = _binding!!
    private val controller = object : MainFragmentAdapter.Controller {
        override fun onLikeItem(item: Item) {
            presenter.likeItem(item)
        }

        override fun onItemClick(item: Item) {
            presenter.onItemClick(item)
        }

        override fun addToCart(item: Item) {
            presenter.addToCart(item)
        }

        override fun isItemLiked(item: Item): Boolean {
            return presenter.isItemLiked(item)
        }

    }
    private val adapter = MainFragmentAdapter(controller)
    private lateinit var promotionAdapter: PromotionAdapter
    private val categoryAdapter = CategoryAdapter()

    companion object {
        fun newInstance() = MainFragment()
    }

    @InjectPresenter
    lateinit var presenter: MainPresenter

    @ProvidePresenter
    fun provideMainPresenter(): MainPresenter =
        Toothpick.openScopes(APP_SCOPE, MAIN_SCOPE)
            .getInstance(MainPresenter::class.java)
            .also { Toothpick.closeScope(MAIN_SCOPE) }


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
        with(binding) {
            val linearLayout = {
                LinearLayoutManager(context).apply {
                    orientation = LinearLayoutManager.HORIZONTAL
                }
            }
            mainFragmentItemRecyclerView.adapter = adapter
            mainFragmentItemRecyclerView.layoutManager = linearLayout()
            promotionAdapter = PromotionAdapter(presenter::navigateToWeb)
            mainFragmentPromotionRecyclerView.adapter = promotionAdapter
            mainFragmentPromotionRecyclerView.layoutManager = linearLayout()
            mainFragmentCategoryRecyclerView.adapter = categoryAdapter
            mainFragmentCategoryRecyclerView.layoutManager = linearLayout()
        }
        presenter.getItemsFromServer()
        presenter.loadPromotions()
        presenter.loadCategories()
    }

    override fun loadItems(list: List<Item>) {
        adapter.setItems(list)
    }

    override fun loadPromotions(list: List<Promotion>) {
        promotionAdapter.setItems(list)
    }

    override fun loadCategories(list: List<Category>) {
        categoryAdapter.setItems(list)
    }

}