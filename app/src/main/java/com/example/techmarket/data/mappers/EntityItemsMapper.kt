package com.example.techmarket.data.mappers

import com.example.techmarket.data.cartDb.CartItemEntity
import com.example.techmarket.data.entities.Item
import com.example.techmarket.data.likesDb.LikedItemEntity
import javax.inject.Inject

class EntityItemsMapper @Inject constructor() {
    fun convertLikedItemToItem(likedItem: LikedItemEntity): Item {
        return with(likedItem) {
            Item(
                id,
                description,
                imageUrl,
                rating,
                price,
                category,
                brand,
                characteristic
            )
        }
    }

    fun convertItemToLikedItem(item: Item): LikedItemEntity {
        return with(item) {
            LikedItemEntity(
                id,
                description,
                imageUrl,
                rating,
                price,
                category,
                brand,
                characteristic
            )
        }
    }

    fun convertCartItemToItem(cartItem: CartItemEntity): Item {
        return with(cartItem) {
            Item(
                id,
                description,
                imageUrl,
                rating,
                price,
                category,
                brand,
                characteristic,
                count
            )
        }
    }

    fun convertItemToCartItem(item: Item): CartItemEntity {
        return with(item) {
            CartItemEntity(
                id,
                description,
                imageUrl,
                rating,
                price,
                category,
                brand,
                count = count
            )
        }
    }
}