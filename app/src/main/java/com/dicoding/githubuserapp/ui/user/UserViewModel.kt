package com.dicoding.githubuserapp.ui.user

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dicoding.githubuserapp.data.remote.ResponseUser
import com.dicoding.githubuserapp.data.remote.User
import com.dicoding.githubuserapp.data.remote.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UserViewModel : ViewModel() {
    val list = MutableLiveData<ArrayList<User>>()
    val getList: LiveData<ArrayList<User>>
        get() = list

    var isNotFound = false

    fun setData(userQuery: String) {
        ApiConfig.getApiService().getSearchUsers(userQuery)
            .enqueue(object : Callback<ResponseUser> {
                override fun onResponse(
                    call: Call<ResponseUser>,
                    response: Response<ResponseUser>
                ) {
                    setQueryNotFound(response.body()?.result == 0)
                    if (response.isSuccessful) {
                        list.postValue(response.body()?.items)
                    }
                }

                override fun onFailure(call: Call<ResponseUser>, t: Throwable) {
                    t.message?.let { Log.d("Fail set list:", it) }
                }
            })
    }

    fun setQueryNotFound(state: Boolean) {
        isNotFound = state
    }
}