package com.example.techmarket.data.likesDb

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.techmarket.data.entities.Category
import com.example.techmarket.data.entities.UserPricePair

@Entity(tableName = "liked_items_entity")
data class LikedItemEntity(
    @PrimaryKey
    val id: String,
    val description: String = "",
    val imageUrl: String = "",
    val rating: MutableMap<String, Float> = mutableMapOf(),
    val price: Int = 0,
    val category: Category = Category.Uncategory,
    val brand: String = "",
    val characteristic: Map<String, String> = emptyMap<String, String>(),
    var sellers: Map<String, UserPricePair> = emptyMap()
)
