package com.developer.valyutaapp.di.modules

import android.app.Application
import androidx.room.Room
import com.developer.valyutaapp.data.repository.*
import com.developer.valyutaapp.core.database.AppDatabase
import com.developer.valyutaapp.data.local.ValuteDao
import com.developer.valyutaapp.data.remote.ValuteService
import com.developer.valyutaapp.core.dispatcher.CoroutineDispatcherProvider
import com.developer.valyutaapp.core.dispatcher.DispatcherProvider
import com.developer.valyutaapp.domain.usecases.ValuteUseCase
import com.developer.valyutaapp.ui.ValuteViewModel
import com.developer.valyutaapp.core.database.SharedPreference
import com.google.gson.FieldNamingPolicy
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

val viewModelModule = module {
    factory { ValuteViewModel(get()) }
}

val connectionInternet = module {
    //single { Connection(get()) }
}

val sharedPreference = module {
    factory { SharedPreference(get()) }
}

val dispatcherProviders = module {
    factory { CoroutineDispatcherProvider() }

//    fun provideDispatcherProviders(): CoroutineDispatcherProvider {
//        return CoroutineDispatcherProvider()
//    }
//    factory<CoroutineDispatcherProvider> { provideDispatcherProviders() }
}

val remoteDataSources = module {
    factory { ValuteRemoteDataSource(get()) }
}

val apiModules = module {
    fun provideValuteApi(retrofit: Retrofit): ValuteService {
        return retrofit.create(ValuteService::class.java)
    }
    single { provideValuteApi(get()) }
}

val netModule = module {
    fun provideCache(application: Application): Cache {
        val cacheSize = 10 * 1024 * 1024
        return Cache(application.cacheDir, cacheSize.toLong())
    }

    fun provideHttpClient(cache: Cache): OkHttpClient {
        val httpInterceptor = HttpLoggingInterceptor()
        httpInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
        val okHttpClientBuilder = OkHttpClient.Builder()
            .apply {
                readTimeout(20, TimeUnit.SECONDS)
                writeTimeout(20, TimeUnit.SECONDS)
                connectTimeout(20, TimeUnit.SECONDS)
            }
            .cache(cache)
        return okHttpClientBuilder.addInterceptor(HttpLoggingInterceptor().apply {
            //  level = if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY else HttpLoggingInterceptor.Level.NONE
            level = HttpLoggingInterceptor.Level.BODY
        }).build()
    }

    fun provideGson(): Gson {
        return GsonBuilder().setFieldNamingPolicy(FieldNamingPolicy.IDENTITY).setLenient().create()
    }

    fun provideRetrofit(factory: Gson, client: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl("http://nbt.tj/ru/kurs/")
            .addConverterFactory(GsonConverterFactory.create(factory))
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .client(client)
            .build()
    }

    single { provideCache(androidApplication()) }
    single { provideHttpClient(get()) }
    single { provideGson() }
    single { provideRetrofit(get(), get()) }

}

val databaseModule = module {

    fun provideDatabase(application: Application): AppDatabase {
        return Room.databaseBuilder(application, AppDatabase::class.java, "currency")
            .fallbackToDestructiveMigration()
            .allowMainThreadQueries()
            .build()
    }

    fun provideDao(database: AppDatabase): ValuteDao {
        return database.valuteDao
    }

    single { provideDatabase(androidApplication()) }
    single { provideDao(get()) }
}

val repositoryModule = module {

    fun provideValuteRemoteRepository(
        dispatcherProvider: DispatcherProvider,
        remoteDataSource: ValuteRemoteDataSource,
        valuteDao: ValuteDao
    ): ValuteRemoteRepositoryImpl {
        return ValuteRemoteRepositoryImpl(dispatcherProvider, remoteDataSource, valuteDao)
    }
    single { provideValuteRemoteRepository(get(), get(), get()) }

    fun provideLocalRepository(valuteDao: ValuteDao): ValuteLocalRepositoryImpl {
        return ValuteLocalRepositoryImpl(valuteDao)
    }
    factory<ValuteLocalRepositoryImpl> { provideLocalRepository(get()) }
}

val useCasesModule = module {
    single { ValuteUseCase() }
}