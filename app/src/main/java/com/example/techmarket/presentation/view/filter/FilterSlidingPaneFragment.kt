package com.example.techmarket.presentation.view.filter

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import androidx.core.view.setPadding
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.example.techmarket.APP_SCOPE
import com.example.techmarket.R
import com.example.techmarket.databinding.FilterSlidingPaneFragmentBinding
import com.example.techmarket.presentation.presenter.FilterSlidingPanePresenter
import com.example.techmarket.presentation.view.base.BaseFragment
import toothpick.Toothpick

const val SLIDING_SCOPE = "SLIDING_SCOPE"

class FilterSlidingPaneFragment : BaseFragment(), FilterSlidingPaneView {
    private var _binding: FilterSlidingPaneFragmentBinding? = null
    private val binding get() = _binding!!

    @InjectPresenter
    lateinit var presenter: FilterSlidingPanePresenter

    @ProvidePresenter
    fun provideFilterSlidingPresenter(): FilterSlidingPanePresenter =
        Toothpick.openScopes(APP_SCOPE, SLIDING_SCOPE)
            .getInstance(FilterSlidingPanePresenter::class.java)
            .also { Toothpick.closeScope(SLIDING_SCOPE) }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FilterSlidingPaneFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter.addCharacteristics()
        binding.filterApplyButton.setOnClickListener { getFilledCharacteristics() }
    }

    override fun addFilterViews(list: List<String>) {
        binding.filterContainer.removeAllViews()
        val displayMetrics = requireContext().resources.displayMetrics
        for (item in list) {
            val characteristicName = TextView(context).apply { text = item }
            val characteristicEditText = EditText(context).apply {
                val layoutParams = ViewGroup.MarginLayoutParams(
                    (300 * displayMetrics.density).toInt(),
                    ViewGroup.LayoutParams.WRAP_CONTENT
                )
                layoutParams.bottomMargin = 320
                this.setPadding(8)
                this.layoutParams = layoutParams

                background = resources.getDrawable(R.drawable.background_button)
            }
            val marginView = View(context).apply {
                layoutParams = ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 32)
            }
            binding.filterContainer.addView(characteristicName)
            binding.filterContainer.addView(characteristicEditText)
            binding.filterContainer.addView(marginView)
        }
    }

    private fun getFilledCharacteristics() {
        val map = mutableMapOf<String, String>()
        for (i in 0 until binding.filterContainer.childCount step 3) {
            val child = binding.filterContainer.getChildAt(i)
            if (child is TextView) {
                val nextChild = (binding.filterContainer.getChildAt(i + 1) as EditText)
                if (nextChild.text.isNotEmpty()) {
                    map[child.text.toString()] = nextChild.text.toString().trim()
                }
            }
        }
        if (map.isNotEmpty()) {
            presenter.applyFilters(map)
        }
    }
}