package com.developer.currency.domain.repository

import com.developer.currency.domain.entities.ValCurs
import com.developer.currency.domain.entities.ValHistory

interface ValuteRemoteRepository {
    suspend fun getAllValutes(date: String, exp: String): ValCurs
    suspend fun getAllHistories(d1: String, d2: String, cn: Int, cs: String, exp: String): ValHistory
}