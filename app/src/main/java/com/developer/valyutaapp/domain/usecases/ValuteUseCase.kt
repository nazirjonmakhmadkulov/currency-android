package com.developer.valyutaapp.domain.usecases

import com.developer.valyutaapp.domain.entities.Valute
import com.developer.valyutaapp.domain.repository.ValuteLocalRepository
import com.developer.valyutaapp.domain.repository.ValuteRemoteRepository

import org.koin.core.component.KoinComponent
import org.koin.core.component.inject


class ValuteUseCase : KoinComponent {
    private val valuteRemoteRepository: ValuteRemoteRepository by inject()
    private val valuteLocalRepository: ValuteLocalRepository by inject()

    //remote
    suspend fun invokeGetRemoteValutes(date: String, exp: String) =
        valuteRemoteRepository.getAllValute(date, exp)

    //local
    fun invokeGetLocalValutes() = valuteLocalRepository.getAllLocalValutes()
    fun invokeGetFavoriteLocalValutes(favorite: String) = valuteLocalRepository.getAllFavoriteLocalValutes(favorite)
    suspend fun invokeGetLocalValuteById(valId: Int) =
        valuteLocalRepository.getLocalValuteById(valId)

    suspend fun invokeGetLocalValuteCount() = valuteLocalRepository.getLocalValuteCount()
    suspend fun invokeUpdateLocalValute(valute: Valute) =
        valuteLocalRepository.updateLocalValute(valute)

    suspend fun invokeDeleteLocalValute(valute: Valute) =
        valuteLocalRepository.deleteLocalValute(valute)

    suspend fun invokeDeleteAllLocalValutes() = valuteLocalRepository.deleteAllLocalValutes()
}