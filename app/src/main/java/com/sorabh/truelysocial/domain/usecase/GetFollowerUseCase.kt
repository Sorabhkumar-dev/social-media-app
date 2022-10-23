package com.sorabh.truelysocial.domain.usecase

import com.sorabh.truelysocial.data.models.Follower
import com.sorabh.truelysocial.data.other.BaseUseCase
import com.sorabh.truelysocial.data.repos.NetworkRepository
import javax.inject.Inject

class GetFollowerUseCase @Inject constructor(private val networkRepository: NetworkRepository) :
    BaseUseCase<Unit, List<Follower>?>() {
    override suspend fun getData(params: Unit?) = networkRepository.getFollowers()

}