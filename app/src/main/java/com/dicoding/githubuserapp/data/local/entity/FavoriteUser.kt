package com.dicoding.githubuserapp.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "favorite_user")
data class FavoriteUser(
    @PrimaryKey val uid: Int,
    @ColumnInfo val username: String?,
    @ColumnInfo val avatar_url: String?
) : Serializable
