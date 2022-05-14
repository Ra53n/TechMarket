package com.example.techmarket.data.repository

import com.example.techmarket.data.Category
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class RepositoryImpl : Repository {

    private val database = Firebase.database.reference

    override fun getMenuItems() = database.child("items").get()

    override fun getPromotions() = database.child("promotions").get()
    override fun getCategories() = listOf(Category.Computers,Category.Smartphones,Category.Appliances,Category.OfficeEquipment)
}