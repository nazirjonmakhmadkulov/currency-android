package com.developer.data.datasource

import com.developer.data.mapping.toModel
import com.developer.database.dao.HistoryDao
import com.developer.domain.model.History
import com.developer.domain.repository.HistoryLocalRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class HistoryLocalRepositoryImpl @Inject constructor(
    private val historyDao: HistoryDao
) : HistoryLocalRepository {
    override fun getLocalHistories(valId: Int, day: Int): Flow<List<History>> = historyDao.getHistories(valId, day)
        .map { histories -> histories.map { history -> history.toModel() } }

    override suspend fun deleteLocalHistory() = historyDao.deleteHistory(0)
    override suspend fun deleteLocalHistories() = historyDao.deleteHistories()
}