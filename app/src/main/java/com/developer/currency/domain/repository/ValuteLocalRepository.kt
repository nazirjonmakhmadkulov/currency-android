package com.developer.currency.domain.repository

import com.developer.currency.domain.entities.Valute
import kotlinx.coroutines.flow.Flow

interface ValuteLocalRepository {
    fun getAllLocalValutes(): Flow<List<Valute>>
    fun getAllFavoriteLocalValutes(): Flow<List<Valute>>
    fun getAllConverterLocalValutes(): Flow<List<Valute>>
    suspend fun getLocalValuteById(valId: Int): Valute
    suspend fun insertLocalValute(valute: Valute)
    suspend fun updateLocalValute(valute: Valute)
    suspend fun updateLocalValuteFromRemote(valute: Valute)
    suspend fun deleteLocalValute(valute: Valute)
    suspend fun deleteAllLocalValutes()
}