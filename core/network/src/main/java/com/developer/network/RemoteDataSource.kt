package com.developer.network

import com.developer.common.result.Result
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.JsonObject
import retrofit2.HttpException
import java.io.IOException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

open class RemoteDataSource {
    open suspend fun <T> safeApiCall(
        dispatcher: CoroutineDispatcher = Dispatchers.IO, apiCall: suspend () -> T
    ): Result<T> {
        return withContext(dispatcher) {
            try {
                Result.Success(apiCall.invoke())
            } catch (throwable: Throwable) {
                when (throwable) {
                    is HttpException -> {
                        val result = when (throwable.code()) {
                            in 400..451 -> parseHttpError(throwable)
                            in 500..599 -> error("Server error")
                            else -> error("Undefined error")
                        }
                        result
                    }

                    is UnknownHostException -> error("No internet connection")
                    is SocketTimeoutException -> error("Slow connection")
                    is IOException -> error(throwable.message)
                    else -> error(throwable.message)
                }
            }
        }
    }

    private fun error(errorMessage: String?): Result.Error {
        return Result.Error(Throwable(message = errorMessage))
    }

    private fun parseHttpError(throwable: HttpException): Result<Nothing> {
        return try {
            val errorBody = throwable.response()?.errorBody()?.string() ?: "Unknown HTTP error body"
            val json = Json {
                ignoreUnknownKeys = true
                isLenient = true
            }
            val errorMessage: JsonElement = try {
                json.parseToJsonElement(errorBody)
            } catch (e: Exception) {
                JsonObject(emptyMap())
            }
            error(errorMessage.toString())
        } catch (exception: Exception) {
            error(exception.localizedMessage)
        }
    }
}