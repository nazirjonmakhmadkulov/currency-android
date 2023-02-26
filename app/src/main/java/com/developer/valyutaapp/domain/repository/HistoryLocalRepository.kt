package com.developer.valyutaapp.domain.repository

import com.developer.valyutaapp.domain.entities.History
import kotlinx.coroutines.flow.Flow

interface HistoryLocalRepository {
    fun getAllLocalHistory(valId: Int, day:Int): Flow<List<History>>
    fun insertLocalHistory(history: History)
    fun updateLocalHistory(history: History)
    fun deleteLocalHistory()
    fun deleteAllLocalHistory()
}