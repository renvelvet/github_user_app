package com.dicoding.githubuserapp.data.local.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.dicoding.githubuserapp.data.local.entity.FavoriteUser

@Dao
interface FavortieUserDao {
    @Query("SELECT * FROM favorite_user")
    fun getAll(): LiveData<List<FavoriteUser>>

    @Query("SELECT COUNT(uid) FROM favorite_user WHERE uid = :id")
    fun findById(id: Int) : Int

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addToFavorite(user: FavoriteUser)

    @Delete
    suspend fun deleteFavoriteUser(user: FavoriteUser)
}