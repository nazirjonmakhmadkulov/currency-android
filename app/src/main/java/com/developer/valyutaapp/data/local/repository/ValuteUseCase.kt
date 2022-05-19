package com.developer.valyutaapp.data.local.repository

import org.koin.core.component.KoinComponent
import org.koin.core.component.inject


class ValuteUseCase : KoinComponent {
    private val valuteRepository: ValuteRepository by inject()
    suspend fun invokeGetAllPaymentType() = valuteRepository.getAllValute()
}