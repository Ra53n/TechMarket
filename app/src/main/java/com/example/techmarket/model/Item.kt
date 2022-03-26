package com.example.techmarket.model

data class Item(
    val type: Type,
    val model: String,
    val year: Int,
    val color: Color,
    val characteristic: MutableMap<String, String>
) {
}

enum class Type { Smartphone }
enum class Color { Black, White, Red, Blue, Grey, Green, Purple, Pink, Yellow, Brown, Orange }