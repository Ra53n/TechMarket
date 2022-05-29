package com.example.techmarket.data.repository.localRepository

import com.example.techmarket.APP_SCOPE
import com.example.techmarket.data.cartDb.CartItemsDatabase
import com.example.techmarket.data.compareDb.CompareItemsDatabase
import com.example.techmarket.data.entities.Item
import com.example.techmarket.data.likesDb.LikedItemsDatabase
import com.example.techmarket.data.mappers.EntityItemsMapper
import toothpick.Toothpick
import javax.inject.Inject

class LocalRepositoryImpl @Inject constructor(
    val databaseLike: LikedItemsDatabase,
    val databaseCart: CartItemsDatabase,
    val databaseCompare: CompareItemsDatabase,
    val mapper: EntityItemsMapper
) : LocalRepository {

    init {
        Toothpick.inject(this, Toothpick.openScope(APP_SCOPE))
    }

    override fun getAllLikedItems(): List<Item> {
        return databaseLike.likedItemsDao().getAllLikedItems()
            .map { mapper.convertLikedItemToItem(it) }
    }

    override fun likeItem(item: Item) {
        databaseLike.likedItemsDao().insert(mapper.convertItemToLikedItem(item))
    }

    override fun deleteLikedItems(item: Item) {
        databaseLike.likedItemsDao().delete(mapper.convertItemToLikedItem(item))
    }

    override fun getAllCartItems(): List<Item> {
        return databaseCart.cartItemsDao().getAllItemsAddedToCart()
            .map { mapper.convertCartItemToItem(it) }
    }

    override fun updateCartItem(item: Item) {
        databaseCart.cartItemsDao().update(mapper.convertItemToCartItem(item))
    }

    override fun addItemToCart(item: Item) {
        databaseCart.cartItemsDao().insert(mapper.convertItemToCartItem(item))
    }

    override fun deleteItemFromCart(item: Item) {
        databaseCart.cartItemsDao().delete(mapper.convertItemToCartItem(item))
    }

    override fun getAllCompareItems(): List<Item> {
        return databaseCompare.compareItemsDao().getAllCompareItems()
            .map { mapper.convertCompareItemToItem(it) }
    }

    override fun addItemToCompare(item: Item) {
        databaseCompare.compareItemsDao().insert(mapper.convertItemToCompareItem(item))
    }

    override fun deleteCompareItem(item: Item) {
        databaseCompare.compareItemsDao().delete(mapper.convertItemToCompareItem(item))
    }

    override fun isItemContainsCompares(item: Item): Boolean {
        return getAllCompareItems().contains(item)
    }
}