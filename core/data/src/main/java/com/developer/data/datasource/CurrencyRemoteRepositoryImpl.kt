package com.developer.data.datasource

import com.developer.common.Utils.dateFormatDb
import com.developer.common.Utils.getDateFormat
import com.developer.common.Utils.getYearAge
import com.developer.data.mapping.toEntity
import com.developer.data.mapping.toModel
import com.developer.database.dao.CurrencyDao
import com.developer.database.dao.HistoryDao
import com.developer.database.entities.CurrencyEntity
import com.developer.database.entities.HistoryEntity
import com.developer.domain.model.Currency
import com.developer.domain.repository.CurrencyRemoteRepository
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class CurrencyRemoteRepositoryImpl @Inject constructor(
    private val currencyRemoteDataSource: CurrencyRemoteDataSource,
    private val currencyDao: CurrencyDao,
    private val historyDao: HistoryDao
) : CurrencyRemoteRepository {

    override suspend fun getCurrencies(date: String, exp: String): List<Currency> {
        val result = currencyRemoteDataSource.getRemoteValutes(date, exp)
        result.second.forEach { insertAndUpdateValute(result.first, it.toEntity()) }
        return currencyDao.getFavoritesCurrencies().map { it.map { it.toModel() } }.first()
    }

    private suspend fun insertAndUpdateValute(dates: String, valute: CurrencyEntity) {
        valute.dates = getDateFormat(dates)
        if (currencyDao.isCurrencyExist(valute.valId)) {
            currencyDao.updateCurrency(
                valute.charCode,
                valute.nominal,
                valute.name,
                valute.value,
                valute.dates,
                valute.valId
            )
        } else {
            if (valute.valId == 840 || valute.valId == 978 || valute.valId == 810) {
                valute.favoritesValute = 1
                valute.favoritesConverter = 1
            }
            currencyDao.insertCurrency(valute)
        }
    }

    override suspend fun getHistories(d1: String, d2: String, cn: Int, cs: String, exp: String) {
        val result = currencyRemoteDataSource.getRemoteHistories(d1, d2, cn, cs, exp)
        result.forEach { insertHistory(it.toEntity()) }
    }

    private suspend fun insertHistory(history: HistoryEntity) {
        if (!historyDao.isCurrencyExist(dateFormatDb(history.dates), history.valId)) {
            val historyNew = HistoryEntity(
                valId = history.valId,
                charCode = history.charCode,
                nominal = history.nominal,
                value = history.value,
                dates = dateFormatDb(history.dates)
            )
            historyDao.insertHistory(historyNew)
        } else {
            if (dateFormatDb(history.dates) < getYearAge()) {
                historyDao.deleteHistory(history.valId)
            }
        }
    }
}