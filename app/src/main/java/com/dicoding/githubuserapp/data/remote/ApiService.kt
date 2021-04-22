package com.dicoding.githubuserapp.data.remote

import com.dicoding.githubuserapp.BuildConfig
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    companion object {
        private const val ENDPOINT_SEARCH_USERS = "search/users"
        private const val ENDPOINT_DETAIL_USER = "users/{username}"
        private const val ENDPOINT_USER_FOLLOWER = "users/{username}/followers"
        private const val ENDPOINT_USER_FOLLOWING = "users/{username}/following"
        private const val TOKEN = BuildConfig.GITHUB_TOKEN_API
    }

    @GET(ENDPOINT_SEARCH_USERS)
    @Headers("Authorization: token $TOKEN")
    fun getSearchUsers(
        @Query("q") userQuery: String
    ): Call<ResponseUser>

    @GET(ENDPOINT_DETAIL_USER)
    @Headers("Authorization: token $TOKEN")
    fun getDetailUser(
        @Path("username") username: String
    ): Call<ResponseDetailUser>

    @GET(ENDPOINT_USER_FOLLOWER)
    @Headers("Authorization: token $TOKEN")
    fun getUserFollower(
        @Path("username") username: String?
    ): Call<ArrayList<User>>

    @GET(ENDPOINT_USER_FOLLOWING)
    @Headers("Authorization: token $TOKEN")
    fun getUserFollowing(
        @Path("username") username: String?
    ): Call<ArrayList<User>>
}