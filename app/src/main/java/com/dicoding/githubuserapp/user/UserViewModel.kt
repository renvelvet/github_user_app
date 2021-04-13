package com.dicoding.githubuserapp.user

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dicoding.githubuserapp.network.ResponseUser
import com.dicoding.githubuserapp.network.User
import com.dicoding.githubuserapp.network.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UserViewModel : ViewModel() {
    val _properties = MutableLiveData<ArrayList<User>>()
    val properties: LiveData<ArrayList<User>>
        get() = _properties

    var isNotFound = false

    fun setData(user_query: String) {
        ApiConfig.getApiService().getSearchUsers(user_query)
            .enqueue(object : Callback<ResponseUser> {
                override fun onResponse(
                    call: Call<ResponseUser>,
                    response: Response<ResponseUser>
                ) {
                    setQueryNotFound(response.body()?.result == 0)
                    if (response.isSuccessful) {
                        _properties.postValue(response.body()?.items)

                        Log.d("hasil", "${response.body()?.result} $isNotFound")

                    }
                }

                override fun onFailure(call: Call<ResponseUser>, t: Throwable) {
                    t.message?.let { Log.d("Fail set _properties:", it) }
                }
            })
    }

    fun setQueryNotFound(state: Boolean) {
        isNotFound = state
    }
}