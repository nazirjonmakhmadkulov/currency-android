package com.developer.valyutaapp.di.modules

import android.app.Application
import androidx.room.Room
import com.developer.valyutaapp.core.common.DB_NAME
import com.developer.valyutaapp.core.common.SERVER_URL
import com.developer.valyutaapp.core.database.AppDatabase
import com.developer.valyutaapp.core.database.SharedPreference
import com.developer.valyutaapp.core.dispatcher.CoroutineDispatcherProvider
import com.developer.valyutaapp.core.dispatcher.DispatcherProvider
import com.developer.valyutaapp.data.local.ValuteDao
import com.developer.valyutaapp.data.remote.ValuteService
import com.developer.valyutaapp.data.repository.ValuteLocalRepositoryImpl
import com.developer.valyutaapp.data.repository.ValuteRemoteDataSource
import com.developer.valyutaapp.data.repository.ValuteRemoteRepositoryImpl
import com.developer.valyutaapp.domain.repository.ValuteLocalRepository
import com.developer.valyutaapp.domain.repository.ValuteRemoteRepository
import com.developer.valyutaapp.domain.usecases.ValuteUseCase
import com.developer.valyutaapp.ui.ValuteViewModel
import com.google.gson.FieldNamingPolicy
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.simplexml.SimpleXmlConverterFactory
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
    fun provideDispatcherProviders(): CoroutineDispatcherProvider {
        return CoroutineDispatcherProvider()
    }
    single<DispatcherProvider> { provideDispatcherProviders() }
}

val remoteDataSources = module {
    fun provideValuteRemoteDataSource(api: ValuteService): ValuteRemoteDataSource {
        return ValuteRemoteDataSource(api)
    }
    factory { provideValuteRemoteDataSource(get()) }
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
            .cache(cache)
            .addInterceptor(httpInterceptor)
            .apply {
                readTimeout(20, TimeUnit.SECONDS)
                writeTimeout(20, TimeUnit.SECONDS)
                connectTimeout(20, TimeUnit.SECONDS)
            }

        return okHttpClientBuilder.build()
    }

    fun provideGson(): Gson {
        return GsonBuilder().setFieldNamingPolicy(FieldNamingPolicy.IDENTITY).setLenient().create()
    }

    fun provideRetrofit(client: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(SERVER_URL)
            //.addCallAdapterFactory(CoroutineCallAdapterFactory.invoke())
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
        return Room.databaseBuilder(application, AppDatabase::class.java, DB_NAME)
            //.fallbackToDestructiveMigration()
            //.allowMainThreadQueries()
            .build()
    }

    fun provideDao(database: AppDatabase): ValuteDao {
        return database.valuteDao()
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
    factory<ValuteRemoteRepository> { provideValuteRemoteRepository(get(), get(), get()) }

    fun provideLocalRepository(valuteDao: ValuteDao): ValuteLocalRepositoryImpl {
        return ValuteLocalRepositoryImpl(valuteDao)
    }
    factory<ValuteLocalRepository> { provideLocalRepository(get()) }
}

val useCasesModule = module {
    factory { ValuteUseCase() }
}