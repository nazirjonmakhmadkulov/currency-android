package com.developer.valyutaapp.data.repository

import com.developer.valyutaapp.data.local.HistoryDao
import com.developer.valyutaapp.domain.entities.History
import com.developer.valyutaapp.domain.repository.HistoryLocalRepository
import kotlinx.coroutines.flow.Flow

class HistoryLocalRepositoryImpl(private val historyDao: HistoryDao) : HistoryLocalRepository {

    override fun getAllLocalHistory(): Flow<List<History>> {
        return historyDao.getAllHistories()
    }

    override suspend fun insertLocalHistory(history: History) {
        historyDao.insertHistory(history)
    }

    override suspend fun updateLocalHistory(history: History) {
        historyDao.updateHistory(history)
    }

    override suspend fun deleteLocalHistory(valId: Int) {
        historyDao.deleteHistory(valId)
    }

    override suspend fun deleteAllLocalHistory() {
        historyDao.deleteAllHistories()
    }
}