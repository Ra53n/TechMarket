package com.example.techmarket.data

import androidx.room.TypeConverter
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
}