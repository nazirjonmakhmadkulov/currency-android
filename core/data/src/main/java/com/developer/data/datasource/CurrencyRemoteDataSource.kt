package com.developer.data.datasource

import com.developer.common.LANGUAGE_RUSSIAN
import com.developer.domain.AppSettings
import com.developer.network.model.CurrencyDto
import com.developer.network.model.HistoryDto
import com.developer.network.retrofit.CurrencyService
import javax.inject.Inject

class CurrencyRemoteDataSource @Inject constructor(
    private val currencyService: CurrencyService,
    private val preference: AppSettings
) {
    private val lang
        get() = preference.language ?: LANGUAGE_RUSSIAN

    suspend fun getRemoteValutes(date: String, exp: String): Pair<String, List<CurrencyDto>> {
        val result = currencyService.getRemoteCurrencies(lang = lang, date = date, exp = exp)
        return Pair(result.dates, result.valute)
    }

    suspend fun getRemoteHistories(
        d1: String, d2: String, cn: Int, cs: String, exp: String
    ): List<HistoryDto> {
        return currencyService
            .getRemoteHistories(lang = lang, d1 = d1, d2 = d2, cn = cn, cs = cs, exp = exp).history
    }
}