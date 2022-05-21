package com.example.techmarket.data.cartDb

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.techmarket.data.StringMapConverter

@Database(entities = [CartItemEntity::class], version = 1, exportSchema = false)
@TypeConverters(StringMapConverter::class)
abstract class CartItemsDatabase : RoomDatabase() {
    abstract fun cartItemsDao(): CartItemsDao
}