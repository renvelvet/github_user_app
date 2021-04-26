package com.dicoding.githubuserapp.ui.sections

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dicoding.githubuserapp.data.model.UserModel
import com.dicoding.githubuserapp.data.remote.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SectionsPagerViewModel : ViewModel() {

    val list = MutableLiveData<ArrayList<UserModel>>()

    fun setData(listType: Int?, username: String?) {
        when (listType) {
            1 -> {
                ApiConfig.getApiService().getUserFollower(username)
                    .enqueue(object : Callback<ArrayList<UserModel>> {
                        override fun onResponse(
                            call: Call<ArrayList<UserModel>>,
                            response: Response<ArrayList<UserModel>>
                        ) {
                            if (response.isSuccessful) {
                                list.postValue(response.body())
                            }
                        }

                        override fun onFailure(call: Call<ArrayList<UserModel>>, t: Throwable) {
                            t.message?.let { Log.d("Fail set follower", it) }
                        }

                    })
            }
            0 -> {
                ApiConfig.getApiService().getUserFollowing(username)
                    .enqueue(object : Callback<ArrayList<UserModel>> {
                        override fun onResponse(
                            call: Call<ArrayList<UserModel>>,
                            response: Response<ArrayList<UserModel>>
                        ) {
                            if (response.isSuccessful) {
                                list.postValue(response.body())
                            }
                        }

                        override fun onFailure(call: Call<ArrayList<UserModel>>, t: Throwable) {
                            t.message?.let { Log.d("Fail set following", it) }
                        }

                    })
            }
        }

    }

    fun getList(): LiveData<ArrayList<UserModel>> {
        return list
    }
}