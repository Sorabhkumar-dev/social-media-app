package com.sorabh.truelysocial.domain.usecase

import com.sorabh.truelysocial.data.models.Comment
import com.sorabh.truelysocial.data.other.BaseUseCase
import com.sorabh.truelysocial.data.repos.NetworkRepository
import javax.inject.Inject

class GetCommentUseCase  @Inject constructor(private val networkRepository: NetworkRepository):BaseUseCase<Int,List<Comment>?>() {
    override suspend fun getData(params: Int?) = networkRepository.getComments(params!!)
}