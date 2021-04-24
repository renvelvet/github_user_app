package com.dicoding.githubuserapp.data.remote

import com.google.gson.annotations.SerializedName

data class ResponseDetailUser(

	@field:SerializedName("following_url")
	val followingUrl: String? = null,

	@field:SerializedName("bio")
	val bio: Any? = null,

	@field:SerializedName("login")
	val username: String? = null,

	@field:SerializedName("company")
	val company: String? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("public_repos")
	val repository: Int = 0,

	@field:SerializedName("followers_url")
	val followersUrl: String? = null,

	@field:SerializedName("followers")
	val followers: Int = 0,

	@field:SerializedName("avatar_url")
	val avatar: String? = null,

	@field:SerializedName("html_url")
	val url: String? = null,

	@field:SerializedName("following")
	val following: Int = 0,

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("location")
	val location: String? = null,
)
