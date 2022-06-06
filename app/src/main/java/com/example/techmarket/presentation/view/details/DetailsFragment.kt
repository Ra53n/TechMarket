package com.example.techmarket.presentation.view.details

import android.os.Build
import android.os.Bundle
import android.view.*
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.core.view.setPadding
import androidx.recyclerview.widget.LinearLayoutManager
import coil.load
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.example.techmarket.APP_SCOPE
import com.example.techmarket.App
import com.example.techmarket.R
import com.example.techmarket.data.entities.Item
import com.example.techmarket.data.entities.User
import com.example.techmarket.databinding.DetailsFragmentBinding
import com.example.techmarket.presentation.presenter.DetailsPresenter
import com.example.techmarket.presentation.view.adapters.SellersAdapter
import com.example.techmarket.presentation.view.base.BaseFragment
import com.google.android.material.divider.MaterialDivider
import toothpick.Toothpick

const val DETAILS_SCOPE = "DETAILS_SCOPE"

class DetailsFragment(private val item: Item) : BaseFragment(), DetailsView {
    private var _binding: DetailsFragmentBinding? = null
    private val binding get() = _binding!!

    private val controller = object : SellersAdapter.Controller {
        override fun addToCart(item: Item, user: User, price: String) {
            presenter.addToCart(item, user, price)
        }

    }

    private val sellersAdapter = SellersAdapter(item, controller)

    companion object {
        fun newInstance(item: Item) = DetailsFragment(item)
    }

    @InjectPresenter
    lateinit var presenter: DetailsPresenter

    @ProvidePresenter
    fun provideDetailsPresenter(): DetailsPresenter =
        Toothpick.openScopes(APP_SCOPE, DETAILS_SCOPE)
            .getInstance(DetailsPresenter::class.java)
            .also { Toothpick.closeScope(DETAILS_SCOPE) }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = DetailsFragmentBinding.inflate(inflater, container, false)
        App.currentUser?.let {
            if (it.seller && !item.sellers.keys.contains(it.id)) {
                setHasOptionsMenu(true)
            }
        }
        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.details_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_sell_item -> {
                showSellersAddingDialog()
            }
        }
        return true
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }


    @RequiresApi(Build.VERSION_CODES.R)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bindItem()
        bindButton()
        presenter.setCheckCompare(item)
        binding.detailsFragmentCompare.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                presenter.addToCompare(item)
            } else {
                presenter.deleteFromCompare(item)
            }
        }
        binding.detailsFragmentSellersRv.adapter = sellersAdapter
        binding.detailsFragmentSellersRv.layoutManager = LinearLayoutManager(context)

        sellersAdapter.setItems(item.sellers)
    }

    @RequiresApi(Build.VERSION_CODES.R)
    private fun bindItem() {
        binding.detailsFragmentDescription.text = item.description
        binding.detailsFragmentImage.load(item.imageUrl)
        binding.detailsFragmentPrice.text = item.price.toString() + " ₽"
        binding.detailsFragmentRatingStar.setOnClickListener { showRatingDialog() }
        var rating = 0.0
        if (item.rating.isNotEmpty()) rating = item.rating.values.average()
        binding.detailsFragmentRating.text = rating.toString()
        val characteristic = item.characteristic.entries.reversed()
        val displayMetrics = requireContext().display
        for (c in characteristic) {
            val tableRow =
                LinearLayout(context).apply {
                    this.layoutParams = LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.WRAP_CONTENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT
                    )
                    this.setPadding(16, 0, 0, 0)
                }
            val characteristicTextView = TextView(context).apply {
                this.text = c.key
                this.textSize = 18f
                this.layoutParams = LinearLayout.LayoutParams(
                    displayMetrics!!.width / 2,
                    ViewGroup.LayoutParams.MATCH_PARENT
                )
            }
            val valueTextView = TextView(context).apply {
                this.textAlignment = View.TEXT_ALIGNMENT_VIEW_END
                this.setPadding(0,0,32,0)
                this.layoutParams = LinearLayout.LayoutParams(
                    displayMetrics!!.width / 2,
                    ViewGroup.LayoutParams.WRAP_CONTENT
                )
                this.text = c.value
                this.textSize = 18f
            }
            val divider = MaterialDivider(requireContext())
            tableRow.addView(characteristicTextView)
            tableRow.addView(valueTextView)
            binding.detailsFragmentCharacteristicContainer.addView(tableRow)
            binding.detailsFragmentCharacteristicContainer.addView(divider)
        }
    }

    private fun bindButton() {
        binding.detailsFragmentLike.setOnClickListener { presenter.likeItem(item) }
        //binding.detailsFragmentBuy.setOnClickListener { presenter.addToCart(item) }
    }

    override fun setContainsCompares(contains: Boolean) {
        binding.detailsFragmentCompare.isChecked = contains
    }

    private fun showSellersAddingDialog() {
        val builder = AlertDialog.Builder(requireContext())
        val editText = EditText(requireContext()).apply { this.hint = "Введите вашу цену!" }
        builder.setTitle("Вы действительно хотите продовать этот товар?")
            .setView(editText)
            .setPositiveButton("Да") { dialog, id ->
                if (editText.text.isNotEmpty()) {
                    val price = editText.text.toString()
                    presenter.addSellerToItem(this.item, price)
                } else {
                    Toast.makeText(requireContext(), "Введите цену", Toast.LENGTH_SHORT)
                }
            }
            .setNegativeButton("Нет") { dialog, id ->
                dialog.cancel()
            }
        builder.create().show()
    }

    private fun showRatingDialog() {
        if (App.currentUser == null) {
            Toast.makeText(
                context,
                "Войдите чтобы оценивать товары!",
                Toast.LENGTH_SHORT
            ).show()
            return
        }
        val builder = AlertDialog.Builder(requireContext())
        val ratingBar = RatingBar(requireContext()).apply { setPadding(8) }
        ratingBar.rating
        builder.setTitle("Вы действительно хотите продовать этот товар?")
            .setView(ratingBar)
            .setPositiveButton("Оценить") { dialog, id ->
                presenter.rateItem(item, ratingBar.rating)
            }
            .setNegativeButton("Нет") { dialog, id ->
                dialog.cancel()
            }
        builder.create().show()
    }
}