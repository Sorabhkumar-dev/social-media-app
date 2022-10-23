package com.sorabh.truelysocial.domain.usecase

import com.sorabh.truelysocial.data.models.Post
import com.sorabh.truelysocial.data.other.BaseUseCase
import com.sorabh.truelysocial.data.repos.NetworkRepository
import javax.inject.Inject

class GetPostUseCase @Inject constructor(private val networkRepository: NetworkRepository) : BaseUseCase<Unit, List<Post>?>() {
    override suspend fun getData(params: Unit?) = networkRepository.getPosts()
}