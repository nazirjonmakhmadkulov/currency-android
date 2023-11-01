package com.developer.currency.domain.usecases

import com.developer.currency.core.common.Result
import com.developer.currency.domain.entities.ValHistory
import com.developer.currency.domain.repository.HistoryLocalRepository
import com.developer.currency.domain.repository.ValuteRemoteRepository
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class HistoryUseCase : KoinComponent {
    private val historyLocalRepository: HistoryLocalRepository by inject()
    private val valuteRemoteRepository: ValuteRemoteRepository by inject()

    // remote
    suspend fun getRemoteHistories(d1: String, d2: String, cn: Int, cs: String, exp: String): Result<ValHistory> =
        valuteRemoteRepository.getAllHistories(d1, d2, cn, cs, exp)

    // local
    fun getLocalHistories(valId: Int, day: Int) = historyLocalRepository.getAllLocalHistory(valId, day)
    suspend fun deleteLocalHistory() = historyLocalRepository.deleteLocalHistory()
    suspend fun deleteAllLocalHistory() = historyLocalRepository.deleteAllLocalHistory()
}