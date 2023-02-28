package com.developer.valyutaapp.data.repository

import android.util.Log
import com.developer.valyutaapp.core.common.Result
import com.developer.valyutaapp.data.local.ValuteDao
import com.developer.valyutaapp.core.dispatcher.DispatcherProvider
import com.developer.valyutaapp.data.local.HistoryDao
import com.developer.valyutaapp.domain.entities.History
import com.developer.valyutaapp.domain.entities.ValCurs
import com.developer.valyutaapp.domain.entities.ValHistory
import com.developer.valyutaapp.domain.entities.Valute
import com.developer.valyutaapp.domain.repository.ValuteRemoteRepository
import com.developer.valyutaapp.utils.Utils.getDateFormat
import com.developer.valyutaapp.utils.Utils.getMonthAge
import com.developer.valyutaapp.utils.Utils.dateFormatDb
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ValuteRemoteRepositoryImpl(
    private val dispatcherProvider: DispatcherProvider,
    private val valuteRemoteDataSource: ValuteRemoteDataSource,
    private val valuteDao: ValuteDao,
    private val historyDao: HistoryDao
) : ValuteRemoteRepository {

    override suspend fun getAllValutes(date: String, exp: String): Result<ValCurs> =
        withContext(Dispatchers.IO) {
            return@withContext when (val result =
                valuteRemoteDataSource.getRemoteValutes(dispatcherProvider.io, date, exp)) {
                is Result.Loading -> Result.Loading
                is Result.Success -> {
                    val valute = result.data.valute
                    valute.forEach { insertAndUpdateValute(result.data.dates, it) }
                    Result.Success(result.data)
                }
                is Result.Error -> Result.Error(result.cause, result.code, result.errorMessage)
            }
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
                valute.valId,
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

    override suspend fun getAllHistories(
        d1: String, d2: String, cn: Int, cs: String, exp: String
    ): Result<ValHistory> = withContext(Dispatchers.IO) {
        return@withContext when (val result =
            valuteRemoteDataSource.getRemoteHistories(dispatcherProvider.io, d1, d2, cn, cs, exp)) {
            is Result.Loading -> Result.Loading
            is Result.Success -> {
                Log.d("getAllHistories", result.data.history.toString())
                val valute = result.data.history
                valute.forEach { insertHistory(it) }
                Result.Success(result.data)
            }
            is Result.Error -> Result.Error(result.cause, result.code, result.errorMessage)
        }
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
            if (dateFormatDb(history.dates) < getMonthAge()) {
                historyDao.deleteHistory(history.valId)
            }
        }
    }
}