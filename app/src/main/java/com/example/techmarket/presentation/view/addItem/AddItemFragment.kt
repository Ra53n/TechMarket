package com.example.techmarket.presentation.view.addItem

import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.EditText
import com.arellomobile.mvp.presenter.InjectPresenter
import com.example.techmarket.R
import com.example.techmarket.data.Category
import com.example.techmarket.data.Item
import com.example.techmarket.databinding.AddItemFragmentBinding
import com.example.techmarket.presentation.presenter.AddItemPresenter
import com.example.techmarket.presentation.view.base.BaseFragment
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

const val ADD_SCOPE = "ADD_SCOPE"

class AddItemFragment : BaseFragment(), AddItemView {
    private var _binding: AddItemFragmentBinding? = null
    private val binding get() = _binding!!
    private lateinit var database: DatabaseReference

    companion object {
        fun newInstance() = AddItemFragment()
    }

    @InjectPresenter
    lateinit var presenter: AddItemPresenter


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = AddItemFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        database = Firebase.database.reference
        binding.buttonAdd.setOnClickListener {
            database.child("items").push().setValue(getItemFromFields())
//            database.child("category").child(Category.Phone.toString())
//                .setValue(
//                    listOf(
//                        "Модель",
//                        "Год релиза",
//                        "Цвет",
//                        "Память",
//                        "Диагональ экрана",
//                        "Операционная система",
//                        "Модель процессора",
//                        "Количество ядер процессора"
//                    )
//                )
        }
        val adapter = ArrayAdapter<Category>(
            requireContext(),
            R.layout.support_simple_spinner_dropdown_item,
            Category.values()
        )
        binding.spinnerCategory.adapter = adapter
        binding.spinnerCategory.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    val category = parent?.getItemAtPosition(position) as Category
                    when (category) {
                        Category.Uncategory -> {
                            binding.characteristicContainer.removeAllViews()
                        }
                        Category.Phone -> {
                            binding.characteristicContainer.removeAllViews()
                            presenter.getPhoneFields()
                        }
                        Category.Computer -> TODO()
                        Category.Appliance -> TODO()
                        Category.Office -> TODO()
                        Category.Entertainment -> TODO()
                    }
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {
                    TODO("Not yet implemented")
                }

            }
    }

    private fun getItemFromFields(): Item {
        val map = mutableMapOf<String, String>()
        for (i in 0 until binding.characteristicContainer.childCount) {
            with(binding.characteristicContainer.getChildAt(i) as EditText) {
                map.put(this.hint.toString(), this.text.toString())
            }
        }
        val item = Item(
            binding.editTextDescription.text.toString(),
            binding.editTextImageUrl.text.toString(),
            0.0,
            binding.editTextImagePrice.text.toString().toInt(),
            characteristic = map
        )
//        (binding.characteristicContainer.getChildAt(0) as EditText).text.toString()
        return item
    }

    override fun addFieldsFromList(list: List<String>) {
        for (s in list) {
            binding.characteristicContainer.addView(EditText(requireContext()).apply {
                hint = s
                gravity = Gravity.CENTER
            })
        }
    }
}