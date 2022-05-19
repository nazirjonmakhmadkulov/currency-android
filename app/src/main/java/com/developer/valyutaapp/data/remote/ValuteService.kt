package com.developer.valyutaapp.data.remote

import com.developer.valyutaapp.domain.entities.ValCurs
import retrofit2.http.GET
import retrofit2.http.Query

interface ValuteService {
    @GET("export_xml.php")
    fun getRemoteValutes(@Query("date") date: String, @Query("export") exp: String): ValCurs
}