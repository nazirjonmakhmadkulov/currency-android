package com.developer.currency.data.repository

import com.developer.currency.data.local.HistoryDao
import com.developer.currency.data.local.ValuteDao
import com.developer.currency.domain.entities.History
import com.developer.currency.domain.entities.ValCurs
import com.developer.currency.domain.entities.ValHistory
import com.developer.currency.domain.entities.Valute
import com.developer.currency.domain.repository.ValuteRemoteRepository
import com.developer.currency.utils.Utils.dateFormatDb
import com.developer.currency.utils.Utils.getDateFormat
import com.developer.currency.utils.Utils.getYearAge

class ValuteRemoteRepositoryImpl(
    private val valuteRemoteDataSource: ValuteRemoteDataSource,
    private val valuteDao: ValuteDao,
    private val historyDao: HistoryDao
) : ValuteRemoteRepository {

    override suspend fun getAllValutes(date: String, exp: String): ValCurs {
        val result = valuteRemoteDataSource.getRemoteValutes(date, exp)
        val valute = result.valute
        valute.forEach { insertAndUpdateValute(result.dates, it) }
        return result
    }

    private suspend fun insertAndUpdateValute(dates: String, valute: Valute) {
        if (valuteDao.getValuteExist(valute.valId)) {
            valute.dates = getDateFormat(dates)
            valuteDao.updateValuteFromRemote(
                valute.charCode,
                valute.nominal,
                valute.name,
                valute.value,
                valute.dates,
                valute.valId
            )
        } else {
            valute.dates = getDateFormat(dates)
            if (valute.valId == 840 || valute.valId == 978 || valute.valId == 810) {
                valute.favoritesValute = 1
                valute.favoritesConverter = 1
            }
            valuteDao.insertValute(valute)
        }
    }

    override suspend fun getAllHistories(d1: String, d2: String, cn: Int, cs: String, exp: String): ValHistory {
        val result = valuteRemoteDataSource.getRemoteHistories(d1, d2, cn, cs, exp)
        val valute = result.history
        valute.forEach { insertHistory(it) }
        return result
    }

    private suspend fun insertHistory(history: History) {
        if (!historyDao.getValuteExist(dateFormatDb(history.dates), history.valId)) {
            val historyNew = History(
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