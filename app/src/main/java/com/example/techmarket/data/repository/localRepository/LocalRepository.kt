package com.example.techmarket.data.repository.localRepository

import com.example.techmarket.data.entities.Item

interface LocalRepository {
    fun getAllLikedItems(): List<Item>

    fun likeItem(item: Item)

    fun deleteLikedItems(item: Item)

    fun getAllCartItems() : List<Item>

    fun updateCartItem(item: Item)

    fun addItemToCart(item: Item)

    fun deleteItemFromCart(item: Item)
}