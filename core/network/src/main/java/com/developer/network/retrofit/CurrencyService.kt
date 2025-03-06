package com.developer.network.retrofit

import com.developer.network.model.CurrenciesDto
import com.developer.network.model.HistoriesDto
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface CurrencyService {
    @GET("{lang}/kurs/export_xml.php")
    suspend fun getRemoteCurrencies(
        @Path("lang") lang: String,
        @Query("date") date: String,
        @Query("export") exp: String
    ): CurrenciesDto

    @GET("{lang}/kurs/export_xml_dynamic.php")
    suspend fun getRemoteHistories(
        @Path("lang") lang: String,
        @Query("d1") d1: String,
        @Query("d2") d2: String,
        @Query("cn") cn: Int,
        @Query("cs") cs: String,
        @Query("export") exp: String
    ): HistoriesDto
}