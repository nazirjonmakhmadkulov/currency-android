package com.developer.valyutaapp.data.repository

import com.developer.valyutaapp.data.local.ValuteDao
import com.developer.valyutaapp.domain.entities.Valute
import com.developer.valyutaapp.domain.repository.ValuteLocalRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext

class ValuteLocalRepositoryImpl(private val valuteDao: ValuteDao) : ValuteLocalRepository {

    override fun getAllLocalValutes(): Flow<List<Valute>> {
        return valuteDao.getAllValutes()
    }

    override fun getAllFavoriteLocalValutes(): Flow<List<Valute>> {
        return valuteDao.getAllFavoritesValutes()
    }

    override fun getAllConverterLocalValutes(): Flow<List<Valute>> {
        return valuteDao.getAllConverterValutes()
    }

    override fun getLocalValuteById(valId: Int): Valute {
        return valuteDao.getValuteById(valId)
    }

    override fun insertLocalValute(valute: Valute) {
        valuteDao.insertValute(valute)
    }

    override suspend fun updateLocalValute(valute: Valute) {
        withContext(Dispatchers.IO){
            valuteDao.updateValute(valute)
        }
    }

    override fun updateLocalValuteFromRemote(valute: Valute) {
        valuteDao.updateValuteFromRemote(
            valute.charCode, valute.nominal, valute.name, valute.value, valute.dates, valute.id
        )
    }

    override fun deleteLocalValute(valute: Valute) {
        valuteDao.deleteValute(valute)
    }

    override fun deleteAllLocalValutes() {
        valuteDao.deleteAllValutes()
    }
}