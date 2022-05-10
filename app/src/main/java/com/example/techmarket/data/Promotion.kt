package com.example.techmarket.data

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Promotion(val imageUrl: String = "", val sourceUrl: String = "") : Parcelable
