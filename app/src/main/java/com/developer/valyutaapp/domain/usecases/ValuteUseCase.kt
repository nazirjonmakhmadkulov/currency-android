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
    suspend fun invokeGetRemoteValutes(date: String, exp: String) = valuteRemoteRepository.getAllValutes(date, exp)

    //local
    fun invokeGetLocalValutes() = valuteLocalRepository.getAllLocalValutes()
    fun invokeGetFavoriteLocalValutes() = valuteLocalRepository.getAllFavoriteLocalValutes()
    fun invokeGetAllConverterLocalValutes() = valuteLocalRepository.getAllConverterLocalValutes()

    suspend fun invokeGetLocalValuteById(valId: Int) = valuteLocalRepository.getLocalValuteById(valId)

    suspend fun invokeUpdateLocalValute(valute: Valute) = valuteLocalRepository.updateLocalValute(valute)

    fun invokeDeleteLocalValute(valute: Valute) = valuteLocalRepository.deleteLocalValute(valute)

    fun invokeDeleteAllLocalValutes() = valuteLocalRepository.deleteAllLocalValutes()
}