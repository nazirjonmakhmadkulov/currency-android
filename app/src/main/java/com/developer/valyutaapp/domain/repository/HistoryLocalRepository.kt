package com.developer.valyutaapp.domain.repository

import com.developer.valyutaapp.domain.entities.History
import kotlinx.coroutines.flow.Flow

interface HistoryLocalRepository {
    fun getAllLocalHistory(): Flow<List<History>>
    suspend fun insertLocalHistory(history: History)
    suspend fun updateLocalHistory(history: History)
    suspend fun deleteLocalHistory(dates: String)
    suspend fun deleteAllLocalHistory()
}