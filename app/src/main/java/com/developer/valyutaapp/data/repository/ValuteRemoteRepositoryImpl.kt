package com.developer.valyutaapp.data.repository

import android.util.Log
import com.developer.valyutaapp.core.common.Result
import com.developer.valyutaapp.data.local.ValuteDao
import com.developer.valyutaapp.core.dispatcher.DispatcherProvider
import com.developer.valyutaapp.data.local.HistoryDao
import com.developer.valyutaapp.domain.entities.History
import com.developer.valyutaapp.domain.entities.ValCurs
import com.developer.valyutaapp.domain.entities.ValHistory
import com.developer.valyutaapp.domain.repository.ValuteRemoteRepository
import com.developer.valyutaapp.utils.Utils.getDateFormat
import com.developer.valyutaapp.utils.Utils.getMonthAge

class ValuteRemoteRepositoryImpl(
    private val dispatcherProvider: DispatcherProvider,
    private val valuteRemoteDataSource: ValuteRemoteDataSource,
    private val valuteDao: ValuteDao,
    private val historyDao: HistoryDao
) : ValuteRemoteRepository {

    override suspend fun getAllValutes(date: String, exp: String): Result<ValCurs> {
        return when (val result =
            valuteRemoteDataSource.getRemoteValutes(dispatcherProvider.io, date, exp)) {
            is Result.Loading -> Result.Loading
            is Result.Success -> {
                val valute = result.data.valute
                if (valuteDao.getValuteExist(getDateFormat(result.data.dates))) {
                    valute.forEach {
                        it.dates = getDateFormat(result.data.dates)
                        valuteDao.updateValuteFromRemote(
                            it.charCode, it.nominal, it.name, it.value, it.dates, it.valId,
                        )
                    }
                } else {
                    valute.forEach {
                        it.dates = getDateFormat(result.data.dates)
                        valuteDao.insertValute(it)
                    }
                }
//                if (!historyDao.getValuteExist(getDateFormat(result.data.dates))) {
//                    valute.forEach {
//                        historyDao.insertHistory(
//                            History(
//                                valId = it.id,
//                                charCode = it.charCode,
//                                nominal = it.nominal,
//                                name = it.name,
//                                value = it.value,
//                                dates = getDateFormat(result.data.dates)
//                            )
//                        )
//                    }
//                }
                Result.Success(result.data)
            }
            is Result.Error -> Result.Error(
                result.cause,
                result.code,
                result.errorMessage
            )
        }
    }

    override suspend fun getAllHistories(
        d1: String, d2: String, cn: Int, cs: String, exp: String
    ): Result<ValHistory> {
        return when (val result =
            valuteRemoteDataSource.getRemoteHistories(dispatcherProvider.io, d1, d2, cn, cs, exp)) {
            is Result.Loading -> Result.Loading
            is Result.Success -> {
                val valute = result.data.history
                Log.d("valute", result.data.toString())
                valute.forEach {
                   // Log.d("getValuteExist", historyDao.getValuteExist(getDateFormat(it.dates)).toString())
                    if (!historyDao.getValuteExist(it.dates)) {
                        historyDao.insertHistory(
                            History(
                                valId = it.valId,
                                charCode = it.charCode,
                                nominal = it.nominal,
                                value = it.value,
                                dates = it.dates
                            )
                        )
                    } else {
                        if (it.dates > getMonthAge()){
                            historyDao.deleteHistory(it.dates)
                        }
                    }
                }
                Result.Success(result.data)
            }
            is Result.Error -> Result.Error(
                result.cause,
                result.code,
                result.errorMessage
            )
        }
    }

}