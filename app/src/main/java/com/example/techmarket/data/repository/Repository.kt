package com.example.techmarket.data.repository

import com.example.techmarket.data.Item

interface Repository {
    fun getMenuItems(): List<Item>
}