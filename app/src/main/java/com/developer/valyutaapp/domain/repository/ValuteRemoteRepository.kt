package com.developer.valyutaapp.domain.repository

import com.developer.valyutaapp.core.common.Result
import com.developer.valyutaapp.domain.entities.ValCurs
import com.developer.valyutaapp.domain.entities.ValHistory
import retrofit2.http.Query

interface ValuteRemoteRepository {
    suspend fun getAllValutes(date: String, exp: String): Result<ValCurs>
    suspend fun getAllHistories(d1: String, d2: String, cn: Int, cs: String, exp: String): Result<ValHistory>
}