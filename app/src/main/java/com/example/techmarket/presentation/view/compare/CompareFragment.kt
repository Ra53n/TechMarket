package com.example.techmarket.presentation.view.compare

import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.HorizontalScrollView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.example.techmarket.APP_SCOPE
import com.example.techmarket.data.entities.Category
import com.example.techmarket.data.entities.Item
import com.example.techmarket.databinding.CompareFragmentBinding
import com.example.techmarket.presentation.presenter.ComparePresenter
import com.example.techmarket.presentation.view.adapters.CompareAdapter
import com.example.techmarket.presentation.view.base.BaseFragment
import com.google.android.material.divider.MaterialDivider
import toothpick.Toothpick

const val COMPARE_SCOPE = "COMPARE_SCOPE"

class CompareFragment : BaseFragment(), CompareView {
    private var _binding: CompareFragmentBinding? = null
    private val binding get() = _binding!!

    companion object {
        fun newInstance() = CompareFragment()
        private const val CHARACTERISTICS_ROW_PADDING = 16
        private const val CHARACTERISTICS_NAME_PADDING = 32
        private const val CHARACTERISTICS_NAME_TEXT_SIZE = 24f
        private const val CHARACTERISTICS_VALUE_TEXT_SIZE = 18f
        private const val CATEGORY_TEXT_VIEW_PADDING = 64
        private const val CATEGORY_TEXT_VIEW_TEXT_SIZE = 24f
    }

    @InjectPresenter
    lateinit var presenter: ComparePresenter

    @ProvidePresenter
    fun provideComparePresenter(): ComparePresenter =
        Toothpick.openScopes(APP_SCOPE, COMPARE_SCOPE)
            .getInstance(ComparePresenter::class.java)
            .also { Toothpick.closeScope(COMPARE_SCOPE) }

    private val controller = object : CompareAdapter.Controller {
        override fun onLikeItem(item: Item) {
            TODO("Not yet implemented")
        }

        override fun onItemClick(item: Item) {
            presenter.onItemClick(item)
        }

        override fun addToCart(item: Item) {
            presenter.addToCart(item)
        }

        override fun onDeleteItemClick(item: Item) {
            presenter.deleteItem(item)
            binding.compareFragmentContainer.removeAllViews()
        }

        override fun isItemLiked(item: Item): Boolean {
            return presenter.isItemLiked(item)
        }

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = CompareFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter.loadItems()
    }

    @RequiresApi(Build.VERSION_CODES.R)
    override fun generateViews(list: List<Item>) {
        val displayMetrics = requireContext().display
        for (category in Category.values()) {
            val categoryList = list.filter { it.category == category }
            if (categoryList.isNotEmpty()) {

                addCategoryViews(category, categoryList)

                val characteristics = categoryList.first().characteristic.keys.reversed()
                val horizontalScrollView = HorizontalScrollView(requireContext()).apply {
                    this.isFillViewport = true
                }
                val characteristicLayout = LinearLayout(requireContext()).apply {
                    this.orientation = LinearLayout.VERTICAL
                }
                for (c in characteristics) {
                    val characteristicsRow =
                        LinearLayout(context).apply {
                            this.setPadding(
                                CHARACTERISTICS_ROW_PADDING,
                                CHARACTERISTICS_ROW_PADDING,
                                0,
                                CHARACTERISTICS_ROW_PADDING
                            )
                        }
                    val characteristicNameTextView =
                        TextView(requireContext()).apply {
                            this.text = c
                            this.textSize = CHARACTERISTICS_NAME_TEXT_SIZE
                            this.setPadding(
                                CHARACTERISTICS_NAME_PADDING,
                                CHARACTERISTICS_NAME_PADDING,
                                0,
                                0
                            )
                        }
                    val dividerFirst = MaterialDivider(requireContext())
                    val dividerSecond = MaterialDivider(requireContext())

                    for (item in categoryList) {
                        val characteristicValueTextView = TextView(requireContext()).apply {
                            this.text = item.characteristic[c]
                            this.textSize = CHARACTERISTICS_VALUE_TEXT_SIZE
                            this.textAlignment = View.TEXT_ALIGNMENT_CENTER
                            this.gravity = Gravity.CENTER

                            this.layoutParams = LinearLayout.LayoutParams(
                                displayMetrics!!.width / 2,
                                ViewGroup.LayoutParams.MATCH_PARENT
                            )
                        }

                        if (categoryList.size == 1) {
                            characteristicValueTextView.layoutParams = LinearLayout.LayoutParams(
                                displayMetrics!!.width,
                                ViewGroup.LayoutParams.WRAP_CONTENT
                            )
                        }

                        characteristicsRow.addView(characteristicValueTextView)
                        val verticalDivider =
                            View(requireContext()).apply { this.setBackgroundColor(Color.GRAY) }

                        characteristicsRow.addView(
                            verticalDivider,
                            ViewGroup.LayoutParams(1, ViewGroup.LayoutParams.MATCH_PARENT)
                        )
                    }
                    with(characteristicLayout) {
                        addView(characteristicNameTextView)
                        addView(dividerFirst)
                        addView(characteristicsRow)
                        addView(dividerSecond)
                    }
                }

                horizontalScrollView.addView(characteristicLayout)
                binding.compareFragmentContainer.addView(horizontalScrollView)
                binding.compareFragmentContainer.addView(MaterialDivider(requireContext()))
            }
        }
    }

    private fun addCategoryViews(category: Category, categoryList: List<Item>) {
        val linearLayout = {
            LinearLayoutManager(context).apply {
                orientation = LinearLayoutManager.HORIZONTAL
            }
        }
        val recyclerView = RecyclerView(requireContext())
        val adapter = CompareAdapter(controller)
        adapter.setItems(categoryList)

        recyclerView.adapter = adapter
        recyclerView.layoutManager = linearLayout.invoke()

        val categoryTextView = TextView(requireContext()).apply {
            this.text = category.category
            this.setPadding(CATEGORY_TEXT_VIEW_PADDING, CATEGORY_TEXT_VIEW_PADDING, 0, 0)
            this.textSize = CATEGORY_TEXT_VIEW_TEXT_SIZE
        }

        with(binding.compareFragmentContainer) {
            addView(categoryTextView)
            addView(recyclerView)
        }
    }
}