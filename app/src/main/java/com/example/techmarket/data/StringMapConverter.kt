package com.example.techmarket.data

import androidx.room.TypeConverter
import com.example.techmarket.data.entities.User
import com.example.techmarket.data.entities.UserPricePair
import com.google.firebase.crashlytics.buildtools.reloc.com.google.common.reflect.TypeToken
import com.google.gson.Gson
import java.lang.reflect.Type

class StringMapConverter {
    @TypeConverter
    fun fromString(value: String?): Map<String?, String?>? {
        val mapType: Type = object : TypeToken<Map<String?, String?>?>() {}.type
        return Gson().fromJson(value, mapType)
    }

    @TypeConverter
    fun fromStringMap(map: Map<String?, String?>?): String? {
        val gson = Gson()
        return gson.toJson(map)
    }

    @TypeConverter
    fun fromSellers(value: String?): Map<String?, UserPricePair?>? {
        val mapType: Type = object : TypeToken<Map<String?, UserPricePair?>?>() {}.type
        return Gson().fromJson(value, mapType)
    }

    @TypeConverter
    fun fromSellersMap(map: Map<String?, UserPricePair?>?): String? {
        val gson = Gson()
        return gson.toJson(map)
    }

    @TypeConverter
    fun fromSelectedSeller(value: String?): User? {
        val userType: Type = object : TypeToken<User?>() {}.type
        return Gson().fromJson(value, userType)
    }

    @TypeConverter
    fun fromSelectedSellerUser(user: User?): String? {
        val gson = Gson()
        return gson.toJson(user)
    }
}