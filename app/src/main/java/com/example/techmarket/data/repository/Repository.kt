package com.example.techmarket.data.repository

import com.example.techmarket.data.Category
import com.google.android.gms.tasks.Task
import com.google.firebase.database.DataSnapshot

interface Repository {
    fun getMenuItems(): Task<DataSnapshot>
    fun getPromotions(): Task<DataSnapshot>
    fun getCategories(): List<Category>
}