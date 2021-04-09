package com.dicoding.githubuserapp.network

import com.dicoding.githubuserapp.model.ResponseDetailUser
import com.dicoding.githubuserapp.model.ResponseUser
import com.dicoding.githubuserapp.model.User
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
    }

    @GET(ENDPOINT_SEARCH_USERS)
    @Headers("Authorization: token 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")
    fun getSearchUsers(
        @Query("q") user_query : String
    ) : Call<ResponseUser>

    @GET(ENDPOINT_DETAIL_USER)
    @Headers("Authorization: token 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")
    fun getDetailUser(
        @Path("username") username : String
    ) : Call<ResponseDetailUser>

    @GET(ENDPOINT_USER_FOLLOWER)
    @Headers("Authorization: token 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")
    fun getUserFollower(
        @Path("username") username: String?
    ) : Call<ArrayList<User>>

    @GET(ENDPOINT_USER_FOLLOWING)
    @Headers("Authorization: token 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")
    fun getUserFollowing(
        @Path("username") username: String?
    ) : Call<ArrayList<User>>
}