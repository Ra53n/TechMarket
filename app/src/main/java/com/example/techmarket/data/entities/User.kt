package com.example.techmarket.data.entities

data class User(
    val email: String,
    val password: String,
    val name: String = "You",
    val seller: Boolean = false,
    val admin: Boolean = false
)
