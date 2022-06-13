package com.developer.valyutaapp.data.repository

import com.developer.valyutaapp.data.local.ValuteDao
import com.developer.valyutaapp.domain.entities.Valute
import com.developer.valyutaapp.domain.repository.ValuteLocalRepository
import kotlinx.coroutines.flow.Flow

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
        return valuteDao.insertValute(valute)
    }

    override fun updateLocalValute(valute: Valute) {
        return valuteDao.updateValute(valute)
    }

    override fun updateLocalValuteFromRemote(valute: Valute) {
        return valuteDao.updateValuteFromRemote(
            valute.charCode, valute.nominal, valute.name, valute.value, valute.dates, valute.id
        )
    }

    override fun deleteLocalValute(valute: Valute) {
        return valuteDao.deleteValute(valute)
    }

    override fun deleteAllLocalValutes() {
        return valuteDao.deleteAllValutes()
    }
}