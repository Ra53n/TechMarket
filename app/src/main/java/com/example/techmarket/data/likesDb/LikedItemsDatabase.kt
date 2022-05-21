package com.example.techmarket.data.likesDb

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.techmarket.data.StringMapConverter

@Database(entities = [LikedItemEntity::class], version = 1, exportSchema = false)
@TypeConverters(StringMapConverter::class)
abstract class LikedItemsDatabase: RoomDatabase() {
    abstract fun likedItemsDao(): LikedItemsDao
}