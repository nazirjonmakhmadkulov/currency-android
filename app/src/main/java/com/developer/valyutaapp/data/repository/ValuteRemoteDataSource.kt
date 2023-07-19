package com.developer.valyutaapp.data.repository

import com.developer.valyutaapp.core.common.Result
import com.developer.valyutaapp.core.database.SharedPreference
import com.developer.valyutaapp.core.network.RemoteDataSource
import com.developer.valyutaapp.data.remote.ValuteService
import com.developer.valyutaapp.domain.entities.ValCurs
import com.developer.valyutaapp.domain.entities.ValHistory
import com.developer.valyutaapp.utils.LocaleManager.Companion.LANGUAGE_RUSSIAN
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
        dispatcher: CoroutineDispatcher, d1: String, d2: String, cn: Int, cs: String, exp: String
    ): Result<ValHistory> {
        return safeApiCall(dispatcher) {
            valuteService.getRemoteHistories(lang = lang, d1 = d1, d2 = d2, cn = cn, cs = cs, exp = exp)
        }
    }
}