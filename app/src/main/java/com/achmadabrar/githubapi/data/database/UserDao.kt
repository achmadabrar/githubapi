package com.achmadabrar.githubapi.data.database

import androidx.room.*
import com.achmadabrar.githubapi.data.model.Item

@Dao
@TypeConverters(ListUserItemConverter::class)
interface UserDao {
    @Query("SELECT * FROM user_table")
    suspend fun getAllListUser(): List<Item>?

    @Query("SELECT * FROM user_table WHERE `login` == :login ")
    suspend fun getUser(login: String?): Item?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUser(listPost: List<Item>)
}