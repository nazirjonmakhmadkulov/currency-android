package com.developer.valyutaapp.domain.usecases

import com.developer.valyutaapp.domain.entities.Valute
import com.developer.valyutaapp.domain.repository.ValuteLocalRepository
import com.developer.valyutaapp.domain.repository.ValuteRemoteRepository
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class ValuteUseCase : KoinComponent {
    private val valuteRemoteRepository: ValuteRemoteRepository by inject()
    private val valuteLocalRepository: ValuteLocalRepository by inject()

    // remote
    suspend fun getRemoteValutes(date: String, exp: String) = valuteRemoteRepository.getAllValutes(date, exp)

    // local
    fun getLocalValutes() = valuteLocalRepository.getAllLocalValutes()
    fun getFavoriteLocalValutes() = valuteLocalRepository.getAllFavoriteLocalValutes()
    fun getAllConverterLocalValutes() = valuteLocalRepository.getAllConverterLocalValutes()

    suspend fun getLocalValuteById(valId: Int) = valuteLocalRepository.getLocalValuteById(valId)
    suspend fun updateLocalValute(valute: Valute) = valuteLocalRepository.updateLocalValute(valute)
    suspend fun deleteLocalValute(valute: Valute) = valuteLocalRepository.deleteLocalValute(valute)
    suspend fun deleteAllLocalValutes() = valuteLocalRepository.deleteAllLocalValutes()
}