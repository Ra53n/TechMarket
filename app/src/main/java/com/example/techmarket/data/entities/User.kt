package com.example.techmarket.data.entities

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import java.util.*

@Parcelize
data class User(
    val id : String = UUID.randomUUID().toString(),
    val email: String = "",
    val password: String = "",
    var name: String = "гость",
    var address: String = "",
    val seller: Boolean = false,
    val admin: Boolean = false
) : Parcelable
