package com.dicoding.githubuserapp.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.dicoding.githubuserapp.data.local.dao.FavortieUserDao
import com.dicoding.githubuserapp.data.local.entity.FavoriteUser

@Database(entities = [FavoriteUser::class], version = 1)
abstract class FavoriteUserDatabase : RoomDatabase() {
    abstract fun favoriteUserDao() : FavortieUserDao
}