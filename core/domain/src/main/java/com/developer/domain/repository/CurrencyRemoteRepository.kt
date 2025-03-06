package com.developer.domain.repository

import com.developer.domain.model.Currency

interface CurrencyRemoteRepository {
    suspend fun getCurrencies(date: String, exp: String): List<Currency>
    suspend fun getHistories(d1: String, d2: String, cn: Int, cs: String, exp: String)
}