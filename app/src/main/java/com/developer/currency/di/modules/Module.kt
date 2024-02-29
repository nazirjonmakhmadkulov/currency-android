package com.developer.currency.di.modules

import android.app.Application
import androidx.room.Room
import com.developer.currency.core.common.DB_NAME
import com.developer.currency.core.common.SERVER_URL
import com.developer.currency.core.database.AppDatabase
import com.developer.currency.core.database.getSharedPreference
import com.developer.currency.core.dispatcher.CoroutineDispatcherProvider
import com.developer.currency.core.dispatcher.DispatcherProvider
import com.developer.currency.core.network.NetworkStatusViewModel
import com.developer.currency.data.local.HistoryDao
import com.developer.currency.data.local.ValuteDao
import com.developer.currency.data.remote.ValuteService
import com.developer.currency.data.repository.HistoryLocalRepositoryImpl
import com.developer.currency.data.repository.ValuteLocalRepositoryImpl
import com.developer.currency.data.repository.ValuteRemoteDataSource
import com.developer.currency.data.repository.ValuteRemoteRepositoryImpl
import com.developer.currency.domain.AppSettings
import com.developer.currency.domain.repository.HistoryLocalRepository
import com.developer.currency.domain.repository.ValuteLocalRepository
import com.developer.currency.domain.repository.ValuteRemoteRepository
import com.developer.currency.domain.usecases.HistoryUseCase
import com.developer.currency.domain.usecases.ValuteUseCase
import com.developer.currency.ui.MainViewModel
import com.developer.currency.ui.chart.ChartViewModel
import com.developer.currency.ui.converter.ConverterViewModel
import com.developer.currency.ui.home.HomeViewModel
import com.developer.currency.ui.setting.SettingsViewModel
import com.developer.currency.ui.valutes.AllValutesViewModel
import com.developer.currency.ui.widget.WidgetViewModel
import com.google.gson.FieldNamingPolicy
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidApplication
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.simplexml.SimpleXmlConverterFactory
import java.util.concurrent.TimeUnit

val viewModelModule = module {
    viewModel { MainViewModel(get()) }
    viewModel { HomeViewModel(get()) }
    viewModel { ConverterViewModel(get()) }
    viewModel { AllValutesViewModel(get()) }
    viewModel { NetworkStatusViewModel() }
    viewModel { ChartViewModel(get(), get()) }
    viewModel { WidgetViewModel(get()) }
    viewModel { SettingsViewModel() }
}

val sharedPreference = module {
    single { androidContext().getSharedPreference() }
    single { AppSettings() }
}

val dispatcherProviders = module {
    fun provideDispatcherProviders(): CoroutineDispatcherProvider {
        return CoroutineDispatcherProvider()
    }
    single<DispatcherProvider> { provideDispatcherProviders() }
}

val remoteDataSources = module {
    fun provideValuteRemoteDataSource(api: ValuteService): ValuteRemoteDataSource {
        return ValuteRemoteDataSource(api)
    }
    single { provideValuteRemoteDataSource(get()) }
}

val apiModules = module {
    fun provideValuteApi(retrofit: Retrofit): ValuteService {
        return retrofit.create(ValuteService::class.java)
    }
    single { provideValuteApi(get()) }
}

val netModule = module {
    fun provideCache(application: Application): Cache {
        val cacheSize = 10 * 10 * 1024
        return Cache(application.cacheDir, cacheSize.toLong())
    }

    fun provideHttpClient(cache: Cache): OkHttpClient {
        val httpInterceptor = HttpLoggingInterceptor()
        httpInterceptor.level = HttpLoggingInterceptor.Level.BODY
        val okHttpClientBuilder = OkHttpClient.Builder()
            .cache(cache).addInterceptor(httpInterceptor)
            .apply {
                readTimeout(200, TimeUnit.SECONDS)
                writeTimeout(200, TimeUnit.SECONDS)
                connectTimeout(200, TimeUnit.SECONDS)
            }

        return okHttpClientBuilder.build()
    }

    fun provideGson(): Gson {
        return GsonBuilder().setFieldNamingPolicy(FieldNamingPolicy.IDENTITY).setLenient().create()
    }

    @Suppress("DEPRECATION")
    fun provideRetrofit(client: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(SERVER_URL)
            .addConverterFactory(SimpleXmlConverterFactory.create())
            .client(client)
            .build()
    }

    single { provideCache(androidApplication()) }
    single { provideHttpClient(get()) }
    single { provideGson() }
    single { provideRetrofit(get()) }
}

val databaseModule = module {
    fun provideDatabase(application: Application): AppDatabase {
        return Room.databaseBuilder(application, AppDatabase::class.java, DB_NAME).build()
    }

    fun provideValuteDao(database: AppDatabase): ValuteDao {
        return database.valuteDao()
    }

    fun provideFavoriteDao(database: AppDatabase): HistoryDao {
        return database.historyDao()
    }
    single { provideDatabase(androidApplication()) }
    single { provideValuteDao(get()) }
    single { provideFavoriteDao(get()) }
}

val repositoryModule = module {
    fun provideValuteRemoteRepository(
        remoteDataSource: ValuteRemoteDataSource, valuteDao: ValuteDao, historyDao: HistoryDao
    ): ValuteRemoteRepositoryImpl {
        return ValuteRemoteRepositoryImpl(remoteDataSource, valuteDao, historyDao)
    }
    single<ValuteRemoteRepository> { provideValuteRemoteRepository(get(), get(), get()) }

    fun provideValuteLocalRepository(valuteDao: ValuteDao): ValuteLocalRepositoryImpl {
        return ValuteLocalRepositoryImpl(valuteDao)
    }
    single<ValuteLocalRepository> { provideValuteLocalRepository(get()) }

    fun provideFavoriteLocalRepository(favoriteDao: HistoryDao): HistoryLocalRepositoryImpl {
        return HistoryLocalRepositoryImpl(favoriteDao)
    }
    single<HistoryLocalRepository> { provideFavoriteLocalRepository(get()) }
}

val useCasesModule = module {
    single { ValuteUseCase() }
    single { HistoryUseCase() }
}