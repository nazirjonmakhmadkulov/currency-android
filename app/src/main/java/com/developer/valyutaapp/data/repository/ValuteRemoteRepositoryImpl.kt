package com.developer.valyutaapp.data.repository

import com.developer.valyutaapp.core.common.Result
import com.developer.valyutaapp.data.local.ValuteDao
import com.developer.valyutaapp.core.dispatcher.DispatcherProvider
import com.developer.valyutaapp.data.local.HistoryDao
import com.developer.valyutaapp.domain.entities.History
import com.developer.valyutaapp.domain.entities.ValCurs
import com.developer.valyutaapp.domain.repository.ValuteRemoteRepository
import com.developer.valyutaapp.utils.Utils.getDateFormat

class ValuteRemoteRepositoryImpl(
    private val dispatcherProvider: DispatcherProvider,
    private val valuteRemoteDataSource: ValuteRemoteDataSource,
    private val valuteDao: ValuteDao,
    private val historyDao: HistoryDao
) : ValuteRemoteRepository {

    override suspend fun getAllValute(date: String, exp: String): Result<ValCurs> {
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
                if (!historyDao.getValuteExist(getDateFormat(result.data.dates))) {
                    valute.forEach {
                        historyDao.insertHistory(
                            History(
                                valId = it.id,
                                charCode = it.charCode,
                                nominal = it.nominal,
                                name = it.name,
                                value = it.value,
                                dates = getDateFormat(result.data.dates)
                            )
                        )
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