package com.example.techmarket.di

import android.content.Context
import androidx.room.Room
import com.example.techmarket.data.cartDb.CartItemsDatabase
import com.example.techmarket.data.likesDb.LikedItemsDatabase
import javax.inject.Inject
import javax.inject.Provider

class CartItemsProvider  @Inject constructor(val context: Context) : Provider<CartItemsDatabase> {
    private val CART_ITEMS_DATABASE = "CartItems.db"

    override fun get(): CartItemsDatabase {
        return Room.databaseBuilder(
            context, CartItemsDatabase::class.java,
            CART_ITEMS_DATABASE
        ).build()
    }
}