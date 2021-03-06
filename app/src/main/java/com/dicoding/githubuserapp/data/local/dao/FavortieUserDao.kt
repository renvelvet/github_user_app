package com.dicoding.githubuserapp.data.local.dao

import android.database.Cursor
import androidx.room.*
import com.dicoding.githubuserapp.data.local.entity.FavoriteUser

@Dao
interface FavortieUserDao {
    @Query("SELECT * FROM favorite_user")
    suspend fun getAll(): List<FavoriteUser>

    @Query("SELECT * FROM favorite_user")
    fun getUsers(): Cursor

    @Query("SELECT COUNT(uid) FROM favorite_user WHERE uid = :id")
    fun findById(id: Int) : Int

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addToFavorite(user: FavoriteUser)

    @Delete
    suspend fun deleteFavoriteUser(user: FavoriteUser)
}