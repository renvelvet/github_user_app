package com.dicoding.githubuserapp.ui.detail

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dicoding.githubuserapp.data.remote.ResponseDetailUser
import com.dicoding.githubuserapp.data.remote.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailUserViewModel : ViewModel() {

    val user = MutableLiveData<ResponseDetailUser>()

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
}