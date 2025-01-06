package com.dmuhia.bgtaskdemoapp.data.di

import android.content.Context
import com.dmuhia.bgtaskdemoapp.data.network.LiveNetworkMonitor
import com.dmuhia.bgtaskdemoapp.data.network.NetworkMonitor
import com.dmuhia.bgtaskdemoapp.data.network.NetworkMonitorInterceptor
import com.dmuhia.bgtaskdemoapp.data.network.ApiService

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.CipherSuite
import okhttp3.ConnectionSpec
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.TlsVersion
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import timber.log.Timber
import java.util.Collections
import java.util.concurrent.TimeUnit
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    @Singleton
    @Provides
    fun provideBaseUrl():String = "https://jsonplaceholder.typicode.com/"

    @Singleton
    @Provides
    fun provideOkHttpClient(spec: ConnectionSpec, liveNetworkMonitor: NetworkMonitor, @ApplicationContext appContext: Context):OkHttpClient
         {
             val loggingInterceptor = HttpLoggingInterceptor { message -> Timber.e(message) }
             loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY

          return  OkHttpClient.Builder()
                .connectTimeout(10, TimeUnit.SECONDS)
                .readTimeout(45, TimeUnit.SECONDS)
                .writeTimeout(15, TimeUnit.SECONDS)
                .retryOnConnectionFailure(false)
                .hostnameVerifier { _, _ -> true }
                .connectionSpecs(Collections.singletonList(spec))
                .addInterceptor(loggingInterceptor)
              //  .addInterceptor(NetworkMonitorInterceptor(liveNetworkMonitor))
                .addInterceptor{chain: Interceptor.Chain ->
                    val originalRequest = chain.request()
                    val requestBuilder = originalRequest.newBuilder()
                        .header("Content-Type","application/json")
                        .addHeader("Accept", "application/json")
                        .header("Connection", "close")
                    val request = requestBuilder.build()
                    Timber.d("Request Headers:::: ${request.headers.toMultimap()}")
                    chain.proceed(request)
                }
                .build()
        }

    @Provides
    fun provideNetworkMonitor(
        @ApplicationContext appContext: Context
    ): NetworkMonitor {
        return LiveNetworkMonitor(appContext)
    }

    @Singleton
    @Provides
    fun provideConnectionSpec(): ConnectionSpec {
        return ConnectionSpec.Builder(ConnectionSpec.MODERN_TLS)
            .tlsVersions(TlsVersion.TLS_1_2)
            .cipherSuites(
                CipherSuite.TLS_ECDHE_ECDSA_WITH_AES_128_GCM_SHA256,
                CipherSuite.TLS_ECDHE_RSA_WITH_AES_128_GCM_SHA256,
                CipherSuite.TLS_DHE_RSA_WITH_AES_128_GCM_SHA256
            )
            .build()
    }


    @Singleton
    @Provides
    fun provideRetrofit(okHttpClient: OkHttpClient, baseUrl: String,gsonConverterFactory: GsonConverterFactory): Retrofit {
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(gsonConverterFactory)
            .client(okHttpClient)
            .build()
    }
    @Singleton
    @Provides
    fun provideGsonConverterFactory(): GsonConverterFactory {
        return GsonConverterFactory.create()
    }
    @Singleton
    @Provides
    fun provideApiService(retrofit: Retrofit): ApiService {
        return retrofit.create(ApiService::class.java)
    }



}