package com.developer.valyutaapp.domain.usecases

import com.developer.valyutaapp.domain.entities.History
import com.developer.valyutaapp.domain.repository.HistoryLocalRepository

import org.koin.core.component.KoinComponent
import org.koin.core.component.inject


class HistoryUseCase : KoinComponent {
    private val favoriteLocalRepository: HistoryLocalRepository by inject()

    //local
    fun invokeGetLocalHistories() = favoriteLocalRepository.getAllLocalHistory()

    suspend fun invokeInsertLocalHistory(history: History) =
        favoriteLocalRepository.insertLocalHistory(history)

    suspend fun invokeUpdateLocalHistory(history: History) =
        favoriteLocalRepository.updateLocalHistory(history)

    suspend fun invokeDeleteLocalHistory(history: History) =
        favoriteLocalRepository.deleteLocalHistory(history)

    suspend fun invokeDeleteAllLocalHistory() = favoriteLocalRepository.deleteAllLocalHistory()
}