package com.sorabh.truelysocial.data.other

sealed class Result<out T> {
    data class Loading<T>(val isLoading:Boolean = true) : Result<T>()
    data class Success<T>(
        val body: T,
        val code: Int,
        val message: String?,
    ) : Result<T>()

    data class Error<T>(val message: String?):Result<T>()
}