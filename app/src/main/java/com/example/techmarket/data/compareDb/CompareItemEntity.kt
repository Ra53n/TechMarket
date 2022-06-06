package com.example.techmarket.data.compareDb

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.techmarket.data.entities.Category

@Entity(tableName = "compare_items_entity")
data class CompareItemEntity(
    @PrimaryKey
    val id: String,
    val description: String = "",
    val imageUrl: String = "",
    val rating: MutableMap<String, Float> = mutableMapOf(),
    val price: Int = 0,
    val category: Category = Category.Uncategory,
    val brand: String = "",
    val characteristic: Map<String, String> = emptyMap<String, String>()
)