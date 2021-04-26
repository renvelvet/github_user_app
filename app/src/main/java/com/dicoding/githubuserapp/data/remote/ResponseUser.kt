package com.dicoding.githubuserapp.data.remote

import com.dicoding.githubuserapp.data.model.UserModel
import com.google.gson.annotations.SerializedName

data class ResponseUser(
    @field:SerializedName("items")
    val items: ArrayList<UserModel>,

    @field:SerializedName("total_count")
    val result: Int
)