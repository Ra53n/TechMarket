package com.example.techmarket.data.categoryRepository

import com.example.techmarket.data.Category
import com.google.android.gms.tasks.Task
import com.google.firebase.database.DataSnapshot

interface CategoryRepository {
    fun getFields(category: Category) : Task<DataSnapshot>
}