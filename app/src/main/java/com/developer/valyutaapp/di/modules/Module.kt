package com.developer.valyutaapp.di.modules

import android.app.Application
import androidx.room.Room
import com.developer.valyutaapp.utils.SharedPreference
import com.google.gson.FieldNamingPolicy
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

val viewModelModule = module {
//    factory { SplashViewModel(get()) }
//    factory { TranslationsViewModel(get()) }
//    single { TopUpViewModel() }
}


val connectionInternet = module {
    //single { Connection(get()) }
}

val sharedPreference = module {
    factory { SharedPreference(get()) }
}


val apiModule = module {
//    fun provideUserApi(retrofit: Retrofit): ServiceBuilder {
//        return retrofit.create(ServiceBuilder::class.java)
//    }

//    single { provideUserApi(get()) }
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
            .baseUrl("http://api.u-pay.tj/api/")
            .addConverterFactory(GsonConverterFactory.create(factory))
            //.addCallAdapterFactory(CoroutineCallAdapterFactory())
            .client(client)
            .build()
    }

    single { provideCache(androidApplication()) }
    single { provideHttpClient(get()) }
    single { provideGson() }
    single { provideRetrofit(get(), get()) }

}

val databaseModule = module {

//    fun provideDatabase(application: Application): AppDatabase {
//        return Room.databaseBuilder(application, AppDatabase::class.java, "upay.database")
//            .fallbackToDestructiveMigration()
//            .allowMainThreadQueries()
//            .build()
//    }
//
//    fun provideDao(database: AppDatabase): PaymentTypeDao {
//        return database.paymentTypeDAO
//    }
//
//    single { provideDatabase(androidApplication()) }
//    single { provideDao(get()) }
}

val repositoryModule = module {
//    fun providePaymentTypeRepository(api: ServiceBuilder): RemoteRepository {
//        return RemoteRepository(api)
//    }
//    single { providePaymentTypeRepository(get()) }
//
//    fun provideLocalRepository(paymentTypeDao: PaymentTypeDao
//    ): PaymentTypeRepositoryImpl {
//        return PaymentTypeRepositoryImpl(paymentTypeDao)
//    }
//    factory <PaymentTypeRepository> { provideLocalRepository(get()) }

}

val useCasesModule = module {
    // single { PaymentTypeUseCase() }
}