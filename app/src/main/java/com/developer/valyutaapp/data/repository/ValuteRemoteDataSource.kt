package com.developer.valyutaapp.data.repository

import com.developer.valyutaapp.core.common.Result
import com.developer.valyutaapp.data.remote.ValuteService
import com.developer.valyutaapp.domain.entities.ValCurs
import com.developer.valyutaapp.core.network.RemoteDataSource
import com.developer.valyutaapp.utils.Utils
import kotlinx.coroutines.CoroutineDispatcher

class ValuteRemoteDataSource(private val serviceBuilder: ValuteService) : RemoteDataSource() {
    suspend fun getRemoteValutes(dispatcher: CoroutineDispatcher, date: String, exp: String
    ): Result<ValCurs> {
        return safeApiCall(dispatcher) {
            serviceBuilder.getRemoteValutes(date, exp)
        }
    }
}