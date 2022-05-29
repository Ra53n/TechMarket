package com.example.techmarket.di.provider

import android.content.Context
import androidx.room.Room
import com.example.techmarket.data.likesDb.LikedItemsDatabase
import javax.inject.Inject
import javax.inject.Provider

class LikedItemsProvider @Inject constructor(val context: Context) : Provider<LikedItemsDatabase> {

    private val LIKED_ITEMS_DATABASE = "LikedItems.db"

    override fun get(): LikedItemsDatabase {
        return Room.databaseBuilder(
            context, LikedItemsDatabase::class.java,
            LIKED_ITEMS_DATABASE
        ).build()
    }
}