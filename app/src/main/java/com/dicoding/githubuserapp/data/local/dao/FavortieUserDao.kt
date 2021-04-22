package com.dicoding.githubuserapp.data.local.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.dicoding.githubuserapp.data.local.entity.FavoriteUser

@Dao
interface FavortieUserDao {
    @Query("SELECT * FROM favorite_user")
    fun getAll(): LiveData<List<FavoriteUser>>

    @Query("SELECT * FROM favorite_user WHERE username LIKE :favUsername LIMIT 1")
    fun findByUsername(favUsername: String) : LiveData<List<FavoriteUser>>

    @Insert
    fun addToFavorite(user: FavoriteUser)

    @Delete
    fun deleteFavoriteUser(user: FavoriteUser)
}