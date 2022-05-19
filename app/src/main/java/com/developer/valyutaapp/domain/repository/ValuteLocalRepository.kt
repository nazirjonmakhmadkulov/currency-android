package com.developer.valyutaapp.domain.repository

import com.developer.valyutaapp.domain.entities.Valute
import kotlinx.coroutines.flow.Flow

interface ValuteLocalRepository {
    fun getAllLocalValutes(): Flow<List<Valute>>
    suspend fun getLocalValuteById(valId: Int): Valute
    suspend fun getLocalValuteCount(): Int
    suspend fun insertLocalValute(valutes: List<Valute>)
    suspend fun updateLocalValute(valute: Valute)
    suspend fun deleteLocalValute(valute: Valute)
    suspend fun deleteAllLocalValutes()
}