package com.example.techmarket.model.repository

import com.example.techmarket.model.Item

class RepositoryImpl : Repository {
    override fun getMenuItems(): List<Item> {
        return listOf(
            Item(
                "6.7 Смартфон Apple iPhone 13 Pro " +
                        "Max 256 ГБ голубой", 1.9, 179_999
            ),
            Item(
                "6.7 Смартфон Apple iPhone 13 Pro " +
                        "Max 256 ГБ голубой", 2.9, 179_999
            ),
            Item(
                "6.7 Смартфон Apple iPhone 13 Pro " +
                        "Max 256 ГБ голубой", 3.9, 179_999
            ),
            Item(
                "6.7 Смартфон Apple iPhone 13 Pro " +
                        "Max 256 ГБ голубой", 4.9, 179_999
            ),
            Item(
                "6.7 Смартфон Apple iPhone 13 Pro " +
                        "Max 256 ГБ голубой", 5.9, 179_999
            )
        )
    }
}