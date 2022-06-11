package com.example.techmarket.presentation.view.cart

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.example.techmarket.APP_SCOPE
import com.example.techmarket.data.cartDb.CartItemEntity
import com.example.techmarket.data.entities.Item
import com.example.techmarket.data.entities.User
import com.example.techmarket.databinding.CartFragmentBinding
import com.example.techmarket.presentation.presenter.CartPresenter
import com.example.techmarket.presentation.view.adapters.CartAdapter
import com.example.techmarket.presentation.view.base.BaseFragment
import toothpick.Toothpick

const val CART_SCOPE = "CART_SCOPE"

class CartFragment : BaseFragment(), CartView {
    private var _binding: CartFragmentBinding? = null
    private val binding get() = _binding!!

    private lateinit var adapter: CartAdapter
    private val controller = object : CartAdapter.Controller {
        override fun onItemClick(item: Item) {
            TODO("Not yet implemented")
        }

        override fun onDeleteClick(item: Item) {
            presenter.deleteLikedItem(item)
        }

        override fun onChangesCountClick(item: Item, increase: Boolean) {
            presenter.changeItemCount(item, increase)
        }

        override fun onSellerChangesClick(item: Item, seller: User, price: String) {
            presenter.changeSeller(item, seller, price)
        }
    }

    @InjectPresenter
    lateinit var presenter: CartPresenter

    @ProvidePresenter
    fun provideCartPresenter(): CartPresenter =
        Toothpick.openScopes(APP_SCOPE, CART_SCOPE)
            .getInstance(CartPresenter::class.java)
            .also { Toothpick.closeScope(CART_SCOPE) }

    companion object {
        fun newInstance() = CartFragment()
    }

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
        adapter = CartAdapter(controller)
        binding.cartFragmentRecyclerView.adapter = adapter
        binding.cartFragmentRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        presenter.loadItems()
        binding.cartFragmentMakeAnOrder.setOnClickListener { presenter.sendOrder() }
    }

    override fun setData(list: List<CartItemEntity>) {
        adapter.setItems(list)
    }

    override fun setTotalCost(cost: Int) {
        binding.cartFragmentInResult.text = cost.toString()
    }

    override fun sendOrder(intent: Intent) {
        startActivity(intent)
    }

    override fun showNoCartItems() {
        binding.cartFragmentNoItemsTextView.visibility = View.VISIBLE
    }

    override fun showAddressErrorToast() {
        Toast.makeText(
            context,
            "Заполните адрес в профиле для оформления заказа",
            Toast.LENGTH_SHORT
        ).show()
    }
}