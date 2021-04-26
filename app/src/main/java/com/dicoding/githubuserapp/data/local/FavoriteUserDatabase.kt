package com.dicoding.githubuserapp.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.dicoding.githubuserapp.data.local.dao.FavortieUserDao
import com.dicoding.githubuserapp.data.local.entity.FavoriteUser

@Database(entities = [FavoriteUser::class], version = 1, exportSchema = false)
abstract class FavoriteUserDatabase : RoomDatabase() {
    abstract fun favoriteUserDao() : FavortieUserDao

    companion object {
        @Volatile
        private var instance: FavoriteUserDatabase? = null

        fun getInstance(context: Context) =
            instance ?: synchronized(this) {
                val temp = Room
                    .databaseBuilder(context, FavoriteUserDatabase::class.java, "favorite_user")
                    .fallbackToDestructiveMigration()
                    .build()

                instance = temp
                temp
            }
    }
}