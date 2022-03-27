package com.example.techmarket.model

sealed class AppState {
    data class Success(val itemList: List<Item>) : AppState()
    object Loading : AppState()

}
