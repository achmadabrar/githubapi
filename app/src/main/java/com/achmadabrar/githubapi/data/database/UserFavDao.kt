package com.achmadabrar.githubapi.data.database

import androidx.room.*
import com.achmadabrar.githubapi.data.model.UserFav

@Dao
@TypeConverters(ListUserFavConverter::class)
interface UserFavDao {
    @Query("SELECT * FROM fav_user_table")
    fun getAllListUser(): List<UserFav>?

    @Query("SELECT * FROM fav_user_table WHERE `login` == :login ")
    suspend fun getUser(login: String?): UserFav?

    @Query("DELETE FROM fav_user_table WHERE `id` == :id")
    fun deleteUserFav(id: Long?)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertUser(listPost: List<UserFav>)
}