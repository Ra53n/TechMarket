package com.example.techmarket.data.compareDb

import androidx.room.*

@Dao
interface CompareItemsDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(entity: CompareItemEntity)

    @Delete
    fun delete(entity: CompareItemEntity)

    @Update
    fun update(entity: CompareItemEntity)

    @Query("SELECT * FROM compare_items_entity")
    fun getAllCompareItems(): List<CompareItemEntity>
}