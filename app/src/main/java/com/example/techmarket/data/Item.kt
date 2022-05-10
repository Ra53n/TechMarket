package com.example.techmarket.data

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Item(
    val description: String = "",
    val imageUrl: String = "",
    val rating: Double = 0.0,
    val price: Int = 0,
    val category: Category = Category.Uncategory,
    val brand: String = "",
    val characteristic: Map<String,String> = emptyMap<String,String>()
) : Parcelable