package com.example.techmarket.data.categoryRepository

import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class CategoryRepositoryImpl: CategoryRepository {
    private val database = Firebase.database.reference

    override fun getPhoneFields() = database.child("category").child("Phone").get()
}