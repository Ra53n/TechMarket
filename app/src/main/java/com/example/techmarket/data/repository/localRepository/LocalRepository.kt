package com.example.techmarket.data.repository.localRepository

import com.example.techmarket.data.cartDb.CartItemEntity
import com.example.techmarket.data.entities.Item
import com.example.techmarket.data.entities.User

interface LocalRepository {
    fun getAllLikedItems(): List<Item>

    fun likeItem(item: Item)

    fun deleteLikedItems(item: Item)

    fun getAllCartItems(): List<CartItemEntity>

    fun updateCartItem(item: Item, user: User?, price : String?)

    fun addItemToCart(item: Item, user: User?,price: String?)

    fun deleteItemFromCart(item: Item)

    fun getAllCompareItems(): List<Item>

    fun addItemToCompare(item: Item)

    fun deleteCompareItem(item: Item)

    fun isItemContainsCompares(item: Item): Boolean
}