package com.dicoding.githubuserapp.ui.favorite

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.room.Room
import com.dicoding.githubuserapp.data.local.FavoriteUserDatabase
import com.dicoding.githubuserapp.data.local.dao.FavortieUserDao
import com.dicoding.githubuserapp.data.local.entity.FavoriteUser
import kotlinx.coroutines.launch

class FavoriteViewModel(application: Application) : AndroidViewModel(application) {

    private val mfavoriteUsers = MutableLiveData<List<FavoriteUser>>()
    val favoriteUsers: LiveData<List<FavoriteUser>> by lazy { mfavoriteUsers }

    private val favoriteDao: FavortieUserDao by lazy {
        Room.databaseBuilder(application, FavoriteUserDatabase::class.java, "favorite_user").build()
            .favoriteUserDao()
    }

    fun getAll() {
        viewModelScope.launch {
            mfavoriteUsers.value = favoriteDao.getAll()
        }
    }

}