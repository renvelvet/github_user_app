package com.dicoding.githubuserapp.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dicoding.githubuserapp.model.ResponseUser
import com.dicoding.githubuserapp.model.User
import com.dicoding.githubuserapp.network.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UserViewModel : ViewModel() {

    val listUsers = MutableLiveData<ArrayList<User>>()

    fun setData(user_query: String) {
        ApiConfig.getApiService().getSearchUsers(user_query)
            .enqueue(object : Callback<ResponseUser>{
                override fun onResponse(
                    call: Call<ResponseUser>,
                    response: Response<ResponseUser>
                ) {
                    if (response.isSuccessful) {
                        listUsers.postValue(response.body()?.items)
                    }
                }

                override fun onFailure(call: Call<ResponseUser>, t: Throwable) {
                    t.message?.let { Log.d("Fail set listUsers", it) }
                }
            })
    }

    fun getSearchUsers() : LiveData<ArrayList<User>> {
        return listUsers
    }
}