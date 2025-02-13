package com.battlecoach.data.remote

import kotlinx.coroutines.flow.*
import timber.log.Timber

inline fun <ResultType, RequestType> networkBoundResource(
    crossinline query: () -> Flow<ResultType>,
    crossinline fetch: suspend () -> RequestType,
    crossinline saveFetchResult: suspend (RequestType) -> Unit,
    crossinline shouldFetch: (ResultType) -> Boolean = { true },
    crossinline onFetchError: suspend (Throwable) -> Unit = { Timber.e(it) }
) = flow {
    val data = query().first()

    val flow = if (shouldFetch(data)) {
        emit(Resource.Loading(data))

        try {
            saveFetchResult(fetch())
            query().map { Resource.Success(it) }
        } catch (throwable: Throwable) {
            onFetchError(throwable)
            query().map { Resource.Error(throwable, it) }
        }
    } else {
        query().map { Resource.Success(it) }
    }

    emitAll(flow)
}

sealed class Resource<T> {
    data class Success<T>(val data: T) : Resource<T>()
    data class Loading<T>(val data: T) : Resource<T>()
    data class Error<T>(val throwable: Throwable, val data: T) : Resource<T>()
} 