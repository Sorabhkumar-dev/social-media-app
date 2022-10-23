package com.sorabh.truelysocial.data.other

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

abstract class BaseUseCase<in P, R> {
    private suspend fun getResponse(param: P?): Flow<Result<R>> =
        flow {
            emit(Result.Loading())
            try {
                emit(getData(param))
            } catch (e: Exception) {
                emit(Result.Error(e.message))
            }
        }

    abstract suspend fun getData(params:P?):Result<R>

    suspend operator fun invoke(param: P?) = getResponse(param)
}