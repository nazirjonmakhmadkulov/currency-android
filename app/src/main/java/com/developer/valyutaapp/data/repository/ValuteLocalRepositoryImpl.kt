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

    override suspend fun getLocalValuteById(valId: Int): Valute {
        return valuteDao.getValuteById(valId)
    }

    override suspend fun getLocalValuteCount(): Int {
        return valuteDao.getValuteCount()
    }

    override suspend fun insertLocalValute(valute: Valute) {
        return valuteDao.insertValute(valute)
    }

    override suspend fun updateLocalValute(valute: Valute) {
        return valuteDao.updateValute(valute)
    }

    override suspend fun deleteLocalValute(valute: Valute) {
        return valuteDao.deleteValute(valute)
    }

    override suspend fun deleteAllLocalValutes() {
        return valuteDao.deleteAllValutes()
    }
}