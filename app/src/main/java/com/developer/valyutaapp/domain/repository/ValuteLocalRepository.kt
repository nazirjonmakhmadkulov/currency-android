package com.developer.valyutaapp.domain.repository

import com.developer.valyutaapp.domain.entities.Valute
import kotlinx.coroutines.flow.Flow

interface ValuteLocalRepository {
    fun getAllLocalValutes(): Flow<List<Valute>>
    fun getAllFavoriteLocalValutes(): Flow<List<Valute>>
    fun getAllConverterLocalValutes(): Flow<List<Valute>>
    fun getLocalValuteById(valId: Int): Valute
    fun insertLocalValute(valute: Valute)
    suspend fun updateLocalValute(valute: Valute)
    fun updateLocalValuteFromRemote(valute: Valute)
    fun deleteLocalValute(valute: Valute)
    fun deleteAllLocalValutes()
}