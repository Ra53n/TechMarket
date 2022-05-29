package com.example.techmarket.data.compareDb

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.techmarket.data.StringMapConverter

@Database(entities = [CompareItemEntity::class], version = 1, exportSchema = false)
@TypeConverters(StringMapConverter::class)
abstract class CompareItemsDatabase : RoomDatabase() {
    abstract fun compareItemsDao(): CompareItemsDao
}