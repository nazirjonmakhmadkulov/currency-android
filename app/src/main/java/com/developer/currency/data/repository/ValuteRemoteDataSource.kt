package com.developer.currency.data.repository

import com.developer.currency.core.database.SharedPreference
import com.developer.currency.data.remote.ValuteService
import com.developer.currency.domain.entities.ValCurs
import com.developer.currency.domain.entities.ValHistory
import com.developer.currency.utils.LocaleManager.Companion.LANGUAGE_RUSSIAN
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class ValuteRemoteDataSource(private val valuteService: ValuteService) : KoinComponent {
    private val prefs: SharedPreference by inject()
    private val lang = prefs.getLang() ?: LANGUAGE_RUSSIAN

    suspend fun getRemoteValutes(date: String, exp: String): ValCurs {
        return valuteService.getRemoteValutes(lang = lang, date = date, exp = exp)
    }

    suspend fun getRemoteHistories(d1: String, d2: String, cn: Int, cs: String, exp: String): ValHistory {
        return valuteService.getRemoteHistories(lang = lang, d1 = d1, d2 = d2, cn = cn, cs = cs, exp = exp)
    }
}