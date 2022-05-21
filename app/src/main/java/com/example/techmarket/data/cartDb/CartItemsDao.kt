package com.example.techmarket.data.cartDb

import androidx.room.*

@Dao
interface CartItemsDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(entity: CartItemEntity)

    @Delete
    fun delete(entity: CartItemEntity)

    @Update
    fun update(entity: CartItemEntity)

    @Query("SELECT * FROM cart_items_entity")
    fun getAllItemsAddedToCart(): List<CartItemEntity>
}