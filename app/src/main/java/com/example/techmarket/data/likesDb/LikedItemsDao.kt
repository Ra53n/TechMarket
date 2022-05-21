package com.example.techmarket.data.likesDb

import androidx.room.*

@Dao
interface LikedItemsDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(entity: LikedItemEntity)

    @Delete
    fun delete(entity: LikedItemEntity)

    @Update
    fun update(entity: LikedItemEntity)

    @Query("SELECT * FROM liked_items_entity")
    fun getAllLikedItems(): List<LikedItemEntity>
}