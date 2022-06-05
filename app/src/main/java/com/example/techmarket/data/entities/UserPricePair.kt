package com.example.techmarket.data.entities

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class UserPricePair(val user: User? = null, val price: String = "") : Parcelable
