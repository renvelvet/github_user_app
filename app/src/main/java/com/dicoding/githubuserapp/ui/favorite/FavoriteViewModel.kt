package com.dicoding.githubuserapp.ui.favorite

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.room.Room
import com.dicoding.githubuserapp.data.local.FavoriteUserDatabase
import com.dicoding.githubuserapp.data.local.dao.FavortieUserDao

class FavoriteViewModel(application: Application) : AndroidViewModel(application) {

    private val favoriteDao: FavortieUserDao by lazy {
        Room.databaseBuilder(application, FavoriteUserDatabase::class.java, "favorite_user").build()
            .favoriteUserDao()
    }

    val favoriteUsers = favoriteDao.getAll()
}