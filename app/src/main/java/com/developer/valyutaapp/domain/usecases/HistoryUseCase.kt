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
    fun invokeGetLocalHistories(valId: Int) = historyLocalRepository.getAllLocalHistory(valId)

    fun invokeInsertLocalHistory(history: History) =
        historyLocalRepository.insertLocalHistory(history)

    fun invokeUpdateLocalHistory(history: History) =
        historyLocalRepository.updateLocalHistory(history)

    fun invokeDeleteLocalHistory() =
        historyLocalRepository.deleteLocalHistory()

    fun invokeDeleteAllLocalHistory() = historyLocalRepository.deleteAllLocalHistory()
}