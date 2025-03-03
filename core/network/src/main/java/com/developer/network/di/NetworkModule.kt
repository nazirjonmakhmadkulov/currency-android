@file:Suppress("DEPRECATION")

package com.developer.network.di

import com.developer.common.SERVER_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.simplexml.SimpleXmlConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal object NetworkModule {

    @Singleton
    @Provides
    fun provideHttpClient(): OkHttpClient {
        val httpInterceptor = HttpLoggingInterceptor()
        httpInterceptor.level = HttpLoggingInterceptor.Level.BODY
        val okHttpClientBuilder = OkHttpClient.Builder()
            .addInterceptor(httpInterceptor).apply {
                readTimeout(30, TimeUnit.SECONDS)
                writeTimeout(30, TimeUnit.SECONDS)
                connectTimeout(30, TimeUnit.SECONDS)
                retryOnConnectionFailure(true)
            }
        return okHttpClientBuilder.build()
    }

    @Singleton
    @Provides
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit =
        Retrofit.Builder()
            .baseUrl(SERVER_URL)
            .addConverterFactory(SimpleXmlConverterFactory.create())
            .client(okHttpClient)
            .build()
}