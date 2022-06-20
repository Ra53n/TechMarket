package com.example.techmarket.presentation.view.adapters

import com.example.techmarket.data.entities.Item
import com.example.techmarket.data.entities.User

interface AdapterController {
    fun onItemClick(item: Item)
    fun onDeleteClick(item: Item)
    fun isItemContainsCompare(item: Item): Boolean
    fun addItemToCompare(item: Item)
    fun deleteItemFromCompare(item: Item)
    fun addToCart(item: Item, user: User?, price: String?)
}