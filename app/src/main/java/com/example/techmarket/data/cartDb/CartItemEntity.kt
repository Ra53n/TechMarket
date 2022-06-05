package com.example.techmarket.data.cartDb

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.techmarket.data.entities.Category
import com.example.techmarket.data.entities.User
import com.example.techmarket.data.entities.UserPricePair

@Entity(tableName = "cart_items_entity")
data class CartItemEntity(
    @PrimaryKey
    val id: String,
    val description: String = "",
    val imageUrl: String = "",
    val rating: Double = 0.0,
    var price: Int = 0,
    val category: Category = Category.Uncategory,
    val brand: String = "",
    val characteristic: Map<String, String> = emptyMap<String, String>(),
    var count: Int = 1,
    var sellers: Map<String,UserPricePair> = emptyMap(),
    var selectedSeller: User? = null
)