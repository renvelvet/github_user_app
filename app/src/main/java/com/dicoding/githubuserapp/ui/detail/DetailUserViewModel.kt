package com.dicoding.githubuserapp.ui.detail

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
import com.dicoding.githubuserapp.data.remote.ApiConfig
import com.dicoding.githubuserapp.data.remote.ResponseDetailUser
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailUserViewModel(application: Application) : AndroidViewModel(application) {

    val user = MutableLiveData<ResponseDetailUser>()

    private val favoriteDao: FavortieUserDao by lazy {
        Room.databaseBuilder(application, FavoriteUserDatabase::class.java, "favorite_user").build()
            .favoriteUserDao()
    }

    val favoriteUsers = favoriteDao.getAll()

    fun setData(username: String) {
        ApiConfig.getApiService().getDetailUser(username)
            .enqueue(object : Callback<ResponseDetailUser> {
                override fun onResponse(
                    call: Call<ResponseDetailUser>,
                    response: Response<ResponseDetailUser>
                ) {
                    if (response.isSuccessful) {
                        user.postValue(response.body())
                    }
                }

                override fun onFailure(call: Call<ResponseDetailUser>, t: Throwable) {
                    t.message?.let { Log.d("Fail set detail user", it) }
                }
            })
    }

    fun getDetailUser(): LiveData<ResponseDetailUser> {
        return user
    }

    fun addUser(username: String, id: Int, avatar: String?) {
        viewModelScope.launch { favoriteDao.addToFavorite(FavoriteUser(id, username, avatar)) }
    }

    fun checkUser(id: Int) : Int {
        return favoriteDao.findById(id)
    }

    fun deleteUser(username: String, id: Int, avatar: String?) {
        viewModelScope.launch { favoriteDao.deleteFavoriteUser(FavoriteUser(id, username, avatar)) }
    }
}