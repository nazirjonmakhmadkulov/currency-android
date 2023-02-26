package com.developer.valyutaapp.data.repository

import com.developer.valyutaapp.data.local.HistoryDao
import com.developer.valyutaapp.domain.entities.History
import com.developer.valyutaapp.domain.repository.HistoryLocalRepository
import kotlinx.coroutines.flow.Flow

class HistoryLocalRepositoryImpl(private val historyDao: HistoryDao) : HistoryLocalRepository {

    override fun getAllLocalHistory(valId: Int, day:Int): Flow<List<History>> {
        return historyDao.getAllHistories(valId, day)
    }

    override fun insertLocalHistory(history: History) {
        historyDao.insertHistory(history)
    }

    override fun updateLocalHistory(history: History) {
        historyDao.updateHistory(history)
    }

    override fun deleteLocalHistory() {}

    override fun deleteAllLocalHistory() {
        historyDao.deleteAllHistories()
    }
}