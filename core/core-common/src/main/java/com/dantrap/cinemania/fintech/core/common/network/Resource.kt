package com.dantrap.cinemania.fintech.core.common.network

sealed class Resource<T> {
    class Success<T>(val data: T) : Resource<T>()
    class Error<T>(val error: ResponseError) : Resource<T>()
    class Loading<T> : Resource<T>()
}
