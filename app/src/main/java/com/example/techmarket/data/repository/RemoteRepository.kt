package com.example.techmarket.data.repository

import com.example.techmarket.data.entities.Category
import com.example.techmarket.data.entities.Item
import com.example.techmarket.data.entities.User
import com.google.android.gms.tasks.Task
import com.google.firebase.database.DataSnapshot

interface RemoteRepository {
    fun getMenuItems(): Task<DataSnapshot>
    fun getPromotions(): Task<DataSnapshot>
    fun getCategories(): List<Category>
    fun getUsers(): Task<DataSnapshot>
    fun updateUser(user: User)
    fun addSellerToItem(item: Item)
    fun rateItem(item: Item, rating: Float)
}