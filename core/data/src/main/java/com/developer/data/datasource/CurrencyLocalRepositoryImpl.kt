package com.developer.data.datasource

import com.developer.data.mapping.toEntity
import com.developer.data.mapping.toModel
import com.developer.database.dao.CurrencyDao
import com.developer.domain.model.Currency
import com.developer.domain.repository.CurrencyLocalRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class CurrencyLocalRepositoryImpl @Inject constructor(
    private val currencyDao: CurrencyDao
) : CurrencyLocalRepository {
    override fun getLocalCurrencies(): Flow<List<Currency>> = currencyDao.getCurrencies()
        .map { currencies -> currencies.map { currency -> currency.toModel() } }

    override fun getFavoriteLocalCurrencies(): Flow<List<Currency>> = currencyDao.getFavoritesCurrencies()
        .map { currencies -> currencies.map { currency -> currency.toModel() } }

    override fun getConverterLocalCurrencies(): Flow<List<Currency>> = currencyDao.getConverterCurrencies()
        .map { currencies -> currencies.map { currency -> currency.toModel() } }

    override suspend fun getLocalCurrencyById(valId: Int): Currency = currencyDao.getCurrencyById(valId).toModel()
    override suspend fun updateLocalCurrency(currency: Currency) = currencyDao.updateCurrency(currency.toEntity())
    override suspend fun deleteLocalCurrencies() = currencyDao.deleteCurrencies()
}