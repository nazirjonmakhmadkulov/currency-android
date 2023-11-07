package com.developer.currency.domain.repository

import com.developer.currency.domain.entities.History
import kotlinx.coroutines.flow.Flow

interface HistoryLocalRepository {
    fun getAllLocalHistory(valId: Int, day: Int): Flow<List<History>>
    suspend fun insertLocalHistory(history: History)
    suspend fun updateLocalHistory(history: History)
    suspend fun deleteLocalHistory()
    suspend fun deleteAllLocalHistory()
}