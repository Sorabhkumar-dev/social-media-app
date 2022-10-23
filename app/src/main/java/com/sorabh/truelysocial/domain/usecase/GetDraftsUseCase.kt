package com.sorabh.truelysocial.domain.usecase

import com.sorabh.truelysocial.data.models.Draft
import com.sorabh.truelysocial.data.other.BaseUseCase
import com.sorabh.truelysocial.data.repos.NetworkRepository
import javax.inject.Inject

class GetDraftsUseCase @Inject constructor(private val networkRepository: NetworkRepository) :
    BaseUseCase<Unit, List<Draft>?>() {
    override suspend fun getData(params: Unit?) = networkRepository.getDrafts()
}