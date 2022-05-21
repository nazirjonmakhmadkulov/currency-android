package com.developer.valyutaapp.data.repository

import com.developer.valyutaapp.core.common.Result
import com.developer.valyutaapp.data.local.ValuteDao
import com.developer.valyutaapp.core.dispatcher.DispatcherProvider
import com.developer.valyutaapp.domain.entities.ValCurs
import com.developer.valyutaapp.domain.repository.ValuteRemoteRepository
import com.developer.valyutaapp.utils.Utils.getDateFormat

class ValuteRemoteRepositoryImpl(
    private val dispatcherProvider: DispatcherProvider,
    private val valuteRemoteDataSource: ValuteRemoteDataSource,
    private val valuteDao: ValuteDao
) : ValuteRemoteRepository {

    override suspend fun getAllValute(date: String, exp: String): Result<ValCurs> {
        return when (val result =
            valuteRemoteDataSource.getRemoteValutes(dispatcherProvider.io, date, exp)) {
            is Result.Loading -> Result.Loading
            is Result.Success -> {
                val valute = result.data.valute
                valute.forEach {
                    it.dates = getDateFormat(result.data.dates)
                    valuteDao.insertValute(it)
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