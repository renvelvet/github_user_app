package com.dicoding.githubuserapp.ui.favorite

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.room.Room
import com.dicoding.githubuserapp.data.local.FavoriteUserDatabase
import com.dicoding.githubuserapp.data.local.dao.FavortieUserDao
import com.dicoding.githubuserapp.data.local.entity.FavoriteUser

class FavoriteViewModel(application: Application) : AndroidViewModel(application) {

    private val _favoriteUsers = MutableLiveData<List<FavoriteUser>>
    val favoriteUsers: LiveData<List<FavoriteUser>> by lazy { _favoriteUsers }

    private val favoriteDao: FavortieUserDao by lazy {
        Room.databaseBuilder(application, FavoriteUserDatabase::class.java, "favorite_user").build()
            .favoriteUserDao()
    }

    fun getAll() : LiveData<List<FavoriteUser>> {
        favoriteUsers = favoriteDao.getAll()
        Log.d("FavoriteViewModel","${favoriteUsers.value}")
        return favoriteUsers
    }

}