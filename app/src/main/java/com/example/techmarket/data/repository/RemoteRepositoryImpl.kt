package com.example.techmarket.data.repository

import com.example.techmarket.App
import com.example.techmarket.data.entities.Category
import com.example.techmarket.data.entities.Item
import com.example.techmarket.data.entities.User
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import javax.inject.Inject

class RemoteRepositoryImpl @Inject constructor() : RemoteRepository {

    private val database = Firebase.database.reference

    override fun getMenuItems() = database.child("items").get()

    override fun getPromotions() = database.child("promotions").get()
    override fun getCategories() = listOf(
        Category.Computers,
        Category.Smartphones,
        Category.Appliances,
        Category.OfficeEquipment
    )

    override fun getUsers() = database.child("users").get()
    override fun updateUser(user: User) {
        database.child("users").child(user.id).setValue(user)
    }

    override fun addSellerToItem(item: Item) {
        database.child("items").child(item.id).setValue(item)
    }

    override fun rateItem(item: Item, rating: Float) {
        item.rating.putIfAbsent(App.currentUser!!.id, rating)
        database.child("items").child(item.id).setValue(item)
    }
}