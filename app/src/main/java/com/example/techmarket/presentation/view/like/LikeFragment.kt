package com.example.techmarket.presentation.view.like

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.example.techmarket.APP_SCOPE
import com.example.techmarket.data.entities.Item
import com.example.techmarket.data.entities.User
import com.example.techmarket.databinding.LikeFragmentBinding
import com.example.techmarket.presentation.presenter.LikePresenter
import com.example.techmarket.presentation.view.adapters.AdapterController
import com.example.techmarket.presentation.view.adapters.LikeAdapter
import com.example.techmarket.presentation.view.base.BaseFragment
import toothpick.Toothpick

const val LIKE_SCOPE = "LIKE_SCOPE"

class LikeFragment : BaseFragment(), LikeView {
    private var _binding: LikeFragmentBinding? = null
    private val binding get() = _binding!!
    private lateinit var adapter: LikeAdapter
    private val controller = object : AdapterController {
        override fun onItemClick(item: Item) {
            presenter.onItemClick(item)
        }

        override fun onDeleteClick(item: Item) {
            presenter.deleteLikedItem(item)
        }

        override fun isItemContainsCompare(item: Item): Boolean {
            return presenter.isItemContainsCompare(item)
        }

        override fun deleteItemFromCompare(item: Item) {
            presenter.deleteFromCompare(item)
        }

        override fun addToCart(item: Item, user: User?, price: String?) {
            presenter.addToCart(item, user, price)
        }

        override fun addItemToCompare(item: Item) {
            presenter.addToCompare(item)
        }
    }

    companion object {
        fun newInstance() = LikeFragment()
    }

    @InjectPresenter
    lateinit var presenter: LikePresenter

    @ProvidePresenter
    fun provideLikedPresenter(): LikePresenter =
        Toothpick.openScopes(APP_SCOPE, LIKE_SCOPE)
            .getInstance(LikePresenter::class.java)
            .also { Toothpick.closeScope(LIKE_SCOPE) }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = LikeFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapter = LikeAdapter(controller)
        binding.likeFragmentRecyclerView.adapter = adapter
        binding.likeFragmentRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        presenter.loadLikedItems()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun setData(list: List<Item>) {
        if (list.isEmpty()) {
            binding.likeFragmentNoItemsTextView.visibility = View.VISIBLE
        } else {
            binding.likeFragmentNoItemsTextView.visibility = View.GONE
        }
        adapter.setItems(list)
    }
}
