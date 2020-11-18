package com.achmadabrar.githubapi.data.network

import com.achmadabrar.githubapi.data.model.ListUser
import com.achmadabrar.githubapi.data.model.ResponseSearchUser
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface GithubApiService {

    companion object {
        private const val SEARCH_USER = "search/users"
        private const val DETAIL_USER = "users/{username}"
        private const val FOLLOWER = "users/{username}/followers"
        private const val FOLLOWING = "users/{username}/following"
        private const val ALL_USER = "users"
    }

    @GET(ALL_USER)
    suspend fun getListUser(@Query("since") since: Int): ListUser

    @GET(SEARCH_USER)
    suspend fun getUser(@Query("q") q: String? = ""): ResponseSearchUser

    @GET(DETAIL_USER)
    suspend fun getDetailUser(@Path("username") username: String): ListUser

    @GET(FOLLOWER)
    suspend fun getFollower(@Path("username") username: String): ListUser

    @GET(FOLLOWING)
    suspend fun getFollowing(@Path("username") username: String): ListUser
}