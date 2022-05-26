package com.developer.valyutaapp.domain.usecases

import com.developer.valyutaapp.domain.entities.History
import com.developer.valyutaapp.domain.repository.HistoryLocalRepository
import com.developer.valyutaapp.domain.repository.ValuteRemoteRepository

import org.koin.core.component.KoinComponent
import org.koin.core.component.inject


class HistoryUseCase : KoinComponent {
    private val historyLocalRepository: HistoryLocalRepository by inject()
    private val valuteRemoteRepository: ValuteRemoteRepository by inject()

    //remote
    suspend fun invokeGetRemoteHistories(d1: String, d2: String, cn: Int, cs: String, exp: String) =
        valuteRemoteRepository.getAllHistories(d1, d2, cn, cs, exp)

    //local
    fun invokeGetLocalHistories() = historyLocalRepository.getAllLocalHistory()

    suspend fun invokeInsertLocalHistory(history: History) =
        historyLocalRepository.insertLocalHistory(history)

    suspend fun invokeUpdateLocalHistory(history: History) =
        historyLocalRepository.updateLocalHistory(history)

    suspend fun invokeDeleteLocalHistory(dates: String) =
        historyLocalRepository.deleteLocalHistory(dates)

    suspend fun invokeDeleteAllLocalHistory() = historyLocalRepository.deleteAllLocalHistory()
}