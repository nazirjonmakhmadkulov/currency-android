package com.developer.valyutaapp.domain.repository

import com.developer.valyutaapp.core.common.Result
import com.developer.valyutaapp.domain.entities.ValCurs

interface ValuteRemoteRepository {
    suspend fun getAllValute(): Result<ValCurs>
}