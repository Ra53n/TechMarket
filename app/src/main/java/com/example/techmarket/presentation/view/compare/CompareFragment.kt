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
    }

    @InjectPresenter
    lateinit var presenter: ComparePresenter

    @ProvidePresenter
    fun provideComparePresenter(): ComparePresenter =
        Toothpick.openScopes(APP_SCOPE, COMPARE_SCOPE)
            .getInstance(ComparePresenter::class.java)
            .also { Toothpick.closeScope(COMPARE_SCOPE) }

    private val controller = object : CompareAdapter.Controller {
        override fun onItemClick(item: Item) {
            TODO("Not yet implemented")
        }

        override fun onDeleteItemClick(item: Item) {
            presenter.deleteItem(item)
            binding.compareFragmentContainer.removeAllViews()
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

                    val linearRow =
                        LinearLayout(context).apply {
                            this.setPadding(16, 16, 0, 16)
                        }

                    val characteristicNameTextView =
                        TextView(requireContext()).apply {
                            this.text = c
                            this.textSize = 24f
                            this.setPadding(32, 32, 0, 0)
                        }

                    val divider = MaterialDivider(requireContext())
                    val divider2 = MaterialDivider(requireContext())

                    for (item in categoryList) {
                        val characteristicValueTextView = TextView(requireContext()).apply {
                            this.text = item.characteristic[c]
                            this.textSize = 18f
                            this.textAlignment = View.TEXT_ALIGNMENT_CENTER
                            this.gravity = Gravity.CENTER

                            this.layoutParams = LinearLayout.LayoutParams(
                                displayMetrics!!.width / 2,
                                ViewGroup.LayoutParams.WRAP_CONTENT
                            )
                        }

                        if (categoryList.size == 1) {
                            characteristicValueTextView.layoutParams = LinearLayout.LayoutParams(
                                displayMetrics!!.width,
                                ViewGroup.LayoutParams.WRAP_CONTENT
                            )
                        }

                        linearRow.addView(characteristicValueTextView)
                        val verticalDivider =
                            View(requireContext()).apply { this.setBackgroundColor(Color.GRAY) }

                        linearRow.addView(
                            verticalDivider,
                            ViewGroup.LayoutParams(1, ViewGroup.LayoutParams.MATCH_PARENT)
                        )
                    }
                    with(characteristicLayout) {
                        addView(characteristicNameTextView)
                        addView(divider)
                        addView(linearRow)
                        addView(divider2)
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
            this.setPadding(64, 64, 0, 0)
            this.textSize = 24f
        }

        with(binding.compareFragmentContainer) {
            addView(categoryTextView)
            addView(recyclerView)
        }
    }
}