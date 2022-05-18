package com.developer.valyutaapp.data.local.repository

import com.developer.valyutaapp.data.local.room.ValuteDao
import com.developer.valyutaapp.model.ValCurs

class ValuteRepositoryImpl (
    private val valuteDao: ValuteDao
        )
    : ValuteRepository {
    //    override suspend fun getAllPaymentType(): List<ValCurs> {
//        //return valuteDao.getValuteById()
//    }
    override suspend fun getAllValute(): List<ValCurs> {
        TODO("Not yet implemented")
    }
}