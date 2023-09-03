package com.developer.valyutaapp.data.remote

import com.developer.valyutaapp.domain.entities.ValCurs
import com.developer.valyutaapp.domain.entities.ValHistory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ValuteService {
    @GET("{lang}/kurs/export_xml.php")
    suspend fun getRemoteValutes(
        @Path("lang") lang: String,
        @Query("date") date: String,
        @Query("export") exp: String
    ): ValCurs

    @GET("{lang}/kurs/export_xml_dynamic.php")
    suspend fun getRemoteHistories(
        @Path("lang") lang: String,
        @Query("d1") d1: String,
        @Query("d2") d2: String,
        @Query("cn") cn: Int,
        @Query("cs") cs: String,
        @Query("export") exp: String
    ): ValHistory
}
