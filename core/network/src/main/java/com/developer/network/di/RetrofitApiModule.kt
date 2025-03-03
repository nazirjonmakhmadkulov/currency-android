package com.developer.network.di

import com.developer.network.retrofit.CurrencyService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RetrofitApiModule {
    @Singleton
    @Provides
    fun provideCurrencyService(retrofit: Retrofit): CurrencyService =
        retrofit.create(CurrencyService::class.java)
}