package com.example.techmarket.presentation.view.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TableRow
import android.widget.TextView
import coil.load
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.example.techmarket.APP_SCOPE
import com.example.techmarket.data.entities.Item
import com.example.techmarket.databinding.DetailsFragmentBinding
import com.example.techmarket.presentation.presenter.DetailsPresenter
import com.example.techmarket.presentation.view.base.BaseFragment
import com.google.android.material.divider.MaterialDivider
import toothpick.Toothpick

const val DETAILS_SCOPE = "DETAILS_SCOPE"

class DetailsFragment(private val item: Item) : BaseFragment(), DetailsView {
    private var _binding: DetailsFragmentBinding? = null
    private val binding get() = _binding!!

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
        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bindItem()
        bindButton()
        presenter.setCheckCompare(item)
        binding.detailsFragmentCompare.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked){
                presenter.addToCompare(item)
            } else {
                presenter.deleteFromCompare(item)
            }
        }
    }

    private fun bindItem() {
        binding.detailsFragmentDescription.text = item.description
        binding.detailsFragmentImage.load(item.imageUrl)
        binding.detailsFragmentPrice.text = item.price.toString() + " ₽"
        binding.detailsFragmentRating.text = item.rating.toString()
        val characteristic = item.characteristic.entries
        for (c in characteristic) {
            val tableRow =
                TableRow(context).apply {
                    this.layoutParams = TableRow.LayoutParams(
                        LinearLayout.LayoutParams.WRAP_CONTENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT
                    )
                    this.setPadding(16, 0, 0, 0)
                }
            val characteristicTextView = TextView(context).apply {
                this.text = c.key
                this.textSize = 18f
                this.width = 480
            }
            val valueTextView = TextView(context).apply {
                this.textAlignment = View.TEXT_ALIGNMENT_VIEW_END
                this.width = 540
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
        binding.detailsFragmentBuy.setOnClickListener { presenter.addToCart(item) }
    }

    override fun setContainsCompares(contains : Boolean) {
        binding.detailsFragmentCompare.isChecked = contains
    }

}