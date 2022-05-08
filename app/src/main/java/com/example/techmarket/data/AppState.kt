package com.example.techmarket.data

sealed class AppState {
    data class Success(val itemList: List<Item>) : AppState()
    object Loading : AppState()

}
