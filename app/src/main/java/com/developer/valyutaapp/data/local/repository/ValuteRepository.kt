package com.developer.valyutaapp.data.local.repository

import com.developer.valyutaapp.model.ValCurs


interface ValuteRepository {
    suspend fun getAllValute(): List<ValCurs>
}