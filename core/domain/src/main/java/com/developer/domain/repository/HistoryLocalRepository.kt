package com.developer.domain.repository

import com.developer.domain.model.History
import kotlinx.coroutines.flow.Flow

interface HistoryLocalRepository {
    fun getLocalHistories(valId: Int, day: Int): Flow<List<History>>
//    suspend fun insertLocalHistory(history: History)
//    suspend fun updateLocalHistory(history: History)
    suspend fun deleteLocalHistory()
    suspend fun deleteLocalHistories()
}