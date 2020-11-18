package com.achmadabrar.githubapi.data.database

import androidx.room.TypeConverter
import com.achmadabrar.githubapi.data.model.Item
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class ListUserItemConverter {
    companion object {
        @TypeConverter
        @JvmStatic
        fun mutableListToString(string: MutableList<Item>): String {
            val type = object : TypeToken<MutableList<Item>>() {}.type
            return Gson().toJson(string, type)
        }

        @TypeConverter
        @JvmStatic
        fun stringToMutableList(string: String): MutableList<Item> {
            val type = object : TypeToken<MutableList<Item>>() {}.type
            return Gson().fromJson(string, type)
        }
    }
}