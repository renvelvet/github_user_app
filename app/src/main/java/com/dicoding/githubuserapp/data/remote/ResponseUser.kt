package com.dicoding.githubuserapp.data.remote

import com.google.gson.annotations.SerializedName

data class ResponseUser(
    @field:SerializedName("items")
    val items: ArrayList<User>,

    @field:SerializedName("total_count")
    val result: Int
)