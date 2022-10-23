package com.sorabh.truelysocial.data.retrofit

import com.sorabh.truelysocial.data.models.*
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface NodeApiInterface {
    @GET("posts")
    suspend fun getPosts():Response<List<Post>>

    @GET("posts/{postId}/comments")
    suspend fun getPostComments(@Path("postId")postId: Int):Response<List<Comment>>

    @GET("photos")
    suspend fun getPhotos():Response<List<Photo>>

    @GET("users")
    suspend fun getFollowers():Response<List<Follower>>

    @GET("todos")
    suspend fun getDrafts():Response<List<Draft>>
}