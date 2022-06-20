package com.example.techmarket.data.repository.localRepository

import com.example.techmarket.APP_SCOPE
import com.example.techmarket.data.cartDb.CartItemEntity
import com.example.techmarket.data.cartDb.CartItemsDatabase
import com.example.techmarket.data.compareDb.CompareItemsDatabase
import com.example.techmarket.data.entities.Item
import com.example.techmarket.data.entities.User
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

    override fun getAllCartItems(): List<CartItemEntity> {
        return databaseCart.cartItemsDao().getAllItemsAddedToCart()
    }

    override fun updateCartItem(item: Item, user: User?, price: String?) {
        databaseCart.cartItemsDao()
            .update(mapper.convertItemToCartItem(item).apply {
                selectedSeller = user
                price?.let { this.price = it.toInt() }
            })
    }

    override fun addItemToCart(item: Item, user: User?, price: String?) {
        databaseCart.cartItemsDao()
            .insert(mapper.convertItemToCartItem(item).apply {
                selectedSeller = user
                price?.let { this.price = price.toInt() }
            })
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
        return getAllCompareItems().map { it.id }.contains(item.id)
    }

    override fun isItemLiked(item: Item): Boolean {
        return getAllLikedItems().map { it.id }.contains(item.id)
    }
}