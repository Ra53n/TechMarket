package com.example.techmarket.data.categoryRepository

import com.example.techmarket.data.entities.Category
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class CategoryRepositoryImpl: CategoryRepository {
    private val database = Firebase.database.reference

    override fun getFields(category: Category) = database.child("category").child(category.category).get()
}