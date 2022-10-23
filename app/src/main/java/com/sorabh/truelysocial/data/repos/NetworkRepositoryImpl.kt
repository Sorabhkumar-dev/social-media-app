package com.sorabh.truelysocial.data.repos

import com.sorabh.truelysocial.data.models.*
import com.sorabh.truelysocial.data.other.Result
import com.sorabh.truelysocial.data.retrofit.NodeApiInterface
import javax.inject.Inject

class NetworkRepositoryImpl @Inject constructor(private val nodeApiInterface: NodeApiInterface) :
    NetworkRepository {
    override suspend fun getPosts(): Result<List<Post>?> {
        val response = nodeApiInterface.getPosts()
        return try {
            if (response.isSuccessful) {
                Result.Success(response.body(), response.code(), response.message())
            } else
                Result.Error(response.message())
        } catch (e: Exception) {
            Result.Error(response.message())
        }
    }

    override suspend fun getComments(postId: Int): Result<List<Comment>?> {
        val response = nodeApiInterface.getPostComments(postId)
        return try {
            if (response.isSuccessful) {
                Result.Success(response.body(), response.code(), response.message())
            } else
                Result.Error(response.message())
        } catch (e: Exception) {
            Result.Error(response.message())
        }
    }

    override suspend fun getPhotos(): Result<List<Photo>?> {
        val response = nodeApiInterface.getPhotos()
        return try {
            if (response.isSuccessful)
                Result.Success(response.body(), response.code(), response.message())
            else {
                Result.Error(response.message())
            }
        } catch (e: Exception) {
            Result.Error(e.message)
        }
    }

    override suspend fun getFollowers(): Result<List<Follower>?> {
        val response = nodeApiInterface.getFollowers()
        return try {
            if (response.isSuccessful)
                Result.Success(response.body(), response.code(), response.message())
            else
                Result.Error(response.message())
        } catch (e: Exception) {
            Result.Error(e.message)
        }
    }

    override suspend fun getDrafts(): Result<List<Draft>?> {
        val response = nodeApiInterface.getDrafts()
        return try {
            if (response.isSuccessful)
                Result.Success(response.body(), response.code(), response.message())
            else
                Result.Error(response.message())
        } catch (e: Exception) {
            Result.Error(e.message)
        }
    }
}