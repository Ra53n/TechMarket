package com.example.techmarket.data.categoryRepository

import com.google.android.gms.tasks.Task
import com.google.firebase.database.DataSnapshot

interface CategoryRepository {
    fun getPhoneFields() : Task<DataSnapshot>
}