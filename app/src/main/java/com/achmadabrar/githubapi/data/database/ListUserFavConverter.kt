package com.achmadabrar.githubapi.data.database

import androidx.room.TypeConverter
import com.achmadabrar.githubapi.data.model.UserFav
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class ListUserFavConverter {
    companion object {
        @TypeConverter
        @JvmStatic
        fun mutableListToString(string: MutableList<UserFav>): String {
            val type = object : TypeToken<MutableList<UserFav>>() {}.type
            return Gson().toJson(string, type)
        }

        @TypeConverter
        @JvmStatic
        fun stringToMutableList(string: String): MutableList<UserFav> {
            val type = object : TypeToken<MutableList<UserFav>>() {}.type
            return Gson().fromJson(string, type)
        }
    }
}