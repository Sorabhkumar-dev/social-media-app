package com.sorabh.truelysocial.data.repos

import com.sorabh.truelysocial.data.models.*
import com.sorabh.truelysocial.data.other.Result

interface NetworkRepository {
    suspend fun getPosts():Result<List<Post>?>

    suspend fun getComments(postId: Int):Result<List<Comment>?>

    suspend fun getPhotos():Result<List<Photo>?>

    suspend fun getFollowers():Result<List<Follower>?>

    suspend fun getDrafts():Result<List<Draft>?>
}