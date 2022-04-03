package com.example.techmarket.model.repository

import com.example.techmarket.model.Item

interface Repository {
    fun getMenuItems():List<Item>
}