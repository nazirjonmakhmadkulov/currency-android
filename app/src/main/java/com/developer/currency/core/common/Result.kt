package com.developer.currency.core.common

import com.developer.currency.core.network.HttpResult

sealed class Result<out T> {
    data object Loading : Result<Nothing>()
    data class Success<out T>(val data: T) : Result<T>()
    data class Error(val cause: HttpResult, val code: Int? = null, val message: String? = null) : Result<Nothing>()
}