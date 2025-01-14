package com.example.data.remote.utils

sealed class NetworkResult <out T: Any> {
    data class Success<out T : Any>(val data: T) : NetworkResult<T>()
    data class ApiError<out T : Any>(val code: Int, val message: String?) : NetworkResult<T>()
    data class ApiException<out T : Any>(val throwable: Throwable) : NetworkResult<T>()
}