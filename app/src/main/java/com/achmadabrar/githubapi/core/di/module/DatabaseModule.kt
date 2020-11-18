package com.achmadabrar.githubapi.core.di.module

import android.app.Application
import androidx.room.Room
import com.achmadabrar.githubapi.data.database.UserDao
import com.achmadabrar.githubapi.data.database.UserDatabase
import com.achmadabrar.githubapi.data.database.UserFavDao
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DatabaseModule {
    @Provides
    @Singleton
    fun provideAppDatabase(application: Application): UserDatabase {
        return Room
            .databaseBuilder(application, UserDatabase::class.java, UserDatabase.DB_NAME)
            .fallbackToDestructiveMigration()
            .allowMainThreadQueries()
            .build()
    }

    @Provides
    fun provideUserDao(appDataBase: UserDatabase): UserDao {
        return appDataBase.userDao()
    }

    @Provides
    fun provideUserFavDao(appDataBase: UserDatabase): UserFavDao {
        return appDataBase.userFavDao()
    }
}