package com.developer.valyutaapp.data.repository

import com.developer.valyutaapp.data.local.HistoryDao
import com.developer.valyutaapp.domain.entities.History
import com.developer.valyutaapp.domain.repository.HistoryLocalRepository
import kotlinx.coroutines.flow.Flow

class HistoryLocalRepositoryImpl(private val historyDao: HistoryDao) : HistoryLocalRepository {
    override fun getAllLocalHistory(valId: Int, day: Int): Flow<List<History>> = historyDao.getAllHistories(valId, day)

    override suspend fun insertLocalHistory(history: History) = historyDao.insertHistory(history)

    override suspend fun updateLocalHistory(history: History) = historyDao.updateHistory(history)

    override suspend fun deleteLocalHistory() = historyDao.deleteHistory(0)

    override suspend fun deleteAllLocalHistory() = historyDao.deleteAllHistories()
}