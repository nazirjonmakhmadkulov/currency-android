package com.developer.valyutaapp.data.remote

import com.developer.valyutaapp.model.ValCurs
import retrofit2.Response
import retrofit2.http.*

interface ServiceBuilder {
    @GET("wallet/owner")
    suspend fun getOwner(@Header("Authorization") token: String): Response<ValCurs>

    @GET("wallet/news")
    suspend fun getNews(@Header("Authorization") token: String): Response<List<ValCurs>>
}