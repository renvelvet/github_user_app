package com.dicoding.githubuserapp.data.remote

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class User(

	@field:SerializedName("login")
	val username: String? = null,

	@field:SerializedName("avatar_url")
	val avatar: String? = null,

	@field:SerializedName("id")
	val id: Int? = null,
) : Parcelable
