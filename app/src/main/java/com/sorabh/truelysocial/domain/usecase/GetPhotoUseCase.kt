package com.sorabh.truelysocial.domain.usecase

import com.sorabh.truelysocial.data.models.Photo
import com.sorabh.truelysocial.data.other.BaseUseCase
import com.sorabh.truelysocial.data.other.Result
import com.sorabh.truelysocial.data.repos.NetworkRepository
import javax.inject.Inject

class GetPhotoUseCase @Inject constructor(private val networkRepository: NetworkRepository) :
    BaseUseCase<Unit, List<Photo>?>() {
    override suspend fun getData(params: Unit?): Result<List<Photo>?> {
        return networkRepository.getPhotos()
    }
}