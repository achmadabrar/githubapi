package com.achmadabrar.githubapi.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.achmadabrar.githubapi.data.model.Item
import com.achmadabrar.githubapi.data.model.UserFav

@Database(
    entities = [Item::class, UserFav::class],
    version = 2,
    exportSchema = true
)
abstract class UserDatabase : RoomDatabase() {

    abstract fun userDao(): UserDao
    abstract fun userFavDao(): UserFavDao

    companion object {

        private var instance: UserDatabase? = null
        private val LOCK = Any()
        const val DB_NAME = "usersgithub.db"

        @JvmStatic
        fun getInstance(context: Context): UserDatabase {
            synchronized(LOCK) {
                if (instance == null) {
                    instance = Room
                        .databaseBuilder(
                            context.applicationContext,
                            UserDatabase::class.java,
                            DB_NAME
                        )
                        .build()
                }
                return instance!!
            }
        }
    }
}