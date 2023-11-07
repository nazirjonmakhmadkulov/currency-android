package com.developer.currency.data.repository

import com.developer.currency.data.local.ValuteDao
import com.developer.currency.domain.entities.Valute
import com.developer.currency.domain.repository.ValuteLocalRepository
import kotlinx.coroutines.flow.Flow

class ValuteLocalRepositoryImpl(private val valuteDao: ValuteDao) : ValuteLocalRepository {
    override fun getAllLocalValutes(): Flow<List<Valute>> = valuteDao.getAllValutes()
    override fun getAllFavoriteLocalValutes(): Flow<List<Valute>> = valuteDao.getAllFavoritesValutes()
    override fun getAllConverterLocalValutes(): Flow<List<Valute>> = valuteDao.getAllConverterValutes()
    override suspend fun getLocalValuteById(valId: Int): Valute = valuteDao.getValuteById(valId)
    override suspend fun insertLocalValute(valute: Valute) = valuteDao.insertValute(valute)
    override suspend fun updateLocalValute(valute: Valute) = valuteDao.updateValute(valute)
    override suspend fun updateLocalValuteFromRemote(valute: Valute) =
        valuteDao.updateValuteFromRemote(
            valute.charCode, valute.nominal, valute.name, valute.value, valute.dates, valute.id
        )
    override suspend fun deleteLocalValute(valute: Valute) = valuteDao.deleteValute(valute)
    override suspend fun deleteAllLocalValutes() = valuteDao.deleteAllValutes()
}