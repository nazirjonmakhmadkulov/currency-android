package com.developer.domain.usecases

import com.developer.domain.model.Currency
import com.developer.domain.repository.CurrencyLocalRepository
import com.developer.domain.repository.CurrencyRemoteRepository
import javax.inject.Inject

class CurrencyUseCase @Inject constructor(
    private val currencyRemoteRepository: CurrencyRemoteRepository,
    private val currencyLocalRepository: CurrencyLocalRepository
) {
    // remote
    suspend fun getRemoteCurrencies(date: String, exp: String) = currencyRemoteRepository.getCurrencies(date, exp)

    // local
    fun getLocalCurrencies() = currencyLocalRepository.getLocalCurrencies()
    fun getFavoriteLocalCurrencies() = currencyLocalRepository.getFavoriteLocalCurrencies()
    fun getConverterLocalCurrencies() = currencyLocalRepository.getConverterLocalCurrencies()

    suspend fun getLocalCurrencyById(valId: Int) = currencyLocalRepository.getLocalCurrencyById(valId)
    suspend fun updateLocalCurrency(currency: Currency) = currencyLocalRepository.updateLocalCurrency(currency)
}