package com.developer.currency.data.repository

import com.developer.currency.core.common.Result
import com.developer.currency.core.database.SharedPreference
import com.developer.currency.core.network.RemoteDataSource
import com.developer.currency.data.remote.ValuteService
import com.developer.currency.domain.entities.ValCurs
import com.developer.currency.domain.entities.ValHistory
import com.developer.currency.utils.LocaleManager.Companion.LANGUAGE_RUSSIAN
import kotlinx.coroutines.CoroutineDispatcher
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class ValuteRemoteDataSource(private val valuteService: ValuteService) : RemoteDataSource(), KoinComponent {
    private val prefs: SharedPreference by inject()
    private val lang = prefs.getLang() ?: LANGUAGE_RUSSIAN

    suspend fun getRemoteValutes(dispatcher: CoroutineDispatcher, date: String, exp: String): Result<ValCurs> {
        return safeApiCall(dispatcher) {
            valuteService.getRemoteValutes(lang = lang, date = date, exp = exp)
        }
    }

    suspend fun getRemoteHistories(
        dispatcher: CoroutineDispatcher,
        d1: String,
        d2: String,
        cn: Int,
        cs: String,
        exp: String
    ): Result<ValHistory> {
        return safeApiCall(dispatcher) {
            valuteService.getRemoteHistories(lang = lang, d1 = d1, d2 = d2, cn = cn, cs = cs, exp = exp)
        }
    }
}