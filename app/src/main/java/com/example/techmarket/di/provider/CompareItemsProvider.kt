package com.example.techmarket.di.provider

import android.content.Context
import androidx.room.Room
import com.example.techmarket.data.compareDb.CompareItemsDatabase
import javax.inject.Inject
import javax.inject.Provider

class CompareItemsProvider @Inject constructor(val context: Context) :
    Provider<CompareItemsDatabase> {
    private val COMPARE_ITEMS_DATABASE = "CompareItems.db"
    override fun get(): CompareItemsDatabase {
        return Room.databaseBuilder(
            context, CompareItemsDatabase::class.java,
            COMPARE_ITEMS_DATABASE
        ).build()
    }
}