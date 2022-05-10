package com.example.techmarket.presentation.view.catalog

import android.content.Context
import android.widget.ArrayAdapter
import com.example.techmarket.R

class CatalogSpinnerAdapterHolder(private val context: Context) {
    val phonesAdapter = ArrayAdapter<String>(
        context,
        R.layout.support_simple_spinner_dropdown_item,
        context.resources.getStringArray(R.array.phones_and_gadgets)
    )

    val computerAdapter = ArrayAdapter<String>(
        context,
        R.layout.support_simple_spinner_dropdown_item,
        context.resources.getStringArray(R.array.computers)
    )

    val appliancesAdapter = ArrayAdapter<String>(
        context,
        R.layout.support_simple_spinner_dropdown_item,
        context.resources.getStringArray(R.array.appliances)
    )

    val officeAdapter = ArrayAdapter<String>(
        context,
        R.layout.support_simple_spinner_dropdown_item,
        context.resources.getStringArray(R.array.office)
    )
}