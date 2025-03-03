package com.developer.domain.repository

import com.developer.domain.model.Currency
import kotlinx.coroutines.flow.Flow

interface CurrencyLocalRepository {
    fun getLocalCurrencies(): Flow<List<Currency>>
    fun getFavoriteLocalCurrencies(): Flow<List<Currency>>
    fun getConverterLocalCurrencies(): Flow<List<Currency>>
    suspend fun getLocalCurrencyById(valId: Int): Currency
//    suspend fun insertLocalValute(valute: Valute)
    suspend fun updateLocalCurrency(currency: Currency)
//    suspend fun updateLocalValuteFromRemote(valute: Valute)
//    suspend fun deleteLocalValute(valute: Valute)
    suspend fun deleteLocalCurrencies()
}