package com.example.techmarket.data.entities

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import java.util.*

@Parcelize
data class Item(
    val id: String = UUID.randomUUID().toString(),
    val description: String = "",
    val imageUrl: String = "",
    val rating: Double = 0.0,
    val price: Int = 0,
    val category: Category = Category.Uncategory,
    val brand: String = "",
    val characteristic: Map<String, String> = emptyMap<String, String>(),
    var count: Int = 1,
    var sellers: Map<String, UserPricePair> = mapOf()
) : Parcelable