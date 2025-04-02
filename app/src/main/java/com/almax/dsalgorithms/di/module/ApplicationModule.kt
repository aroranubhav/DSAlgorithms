package com.almax.dsalgorithms.di.module

import android.app.Application
import android.content.Context
import com.almax.dsalgorithms.data.remote.AuthenticationInterceptor
import com.almax.dsalgorithms.data.remote.CacheInterceptor
import com.almax.dsalgorithms.data.remote.NetworkService
import com.almax.dsalgorithms.di.ApplicationContext
import com.almax.dsalgorithms.di.BaseUrl
import com.almax.dsalgorithms.util.DefaultDispatcherProvider
import com.almax.dsalgorithms.util.DispatcherProvider
import dagger.Module
import dagger.Provides
import okhttp3.Cache
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File
import javax.inject.Singleton

@Module
class ApplicationModule(
    private val application: Application
) {

    @ApplicationContext
    @Provides
    fun provideContext(): Context =
        application

    @BaseUrl
    @Provides
    fun provideBaseUrl(): String =
        "https://api.github.com/repos/aroranubhav/Data-Structures-and-Algorithms/"

    @Provides
    fun provideGsonConverterFactory(): GsonConverterFactory =
        GsonConverterFactory.create()

    @Provides
    @Singleton
    fun provideHttpClient(): OkHttpClient =
        OkHttpClient()
            .newBuilder()
            .addInterceptor(AuthenticationInterceptor())
            .cache(Cache(File(application.cacheDir, "http-cache"), 10L * 1024L * 1024L))
            .addNetworkInterceptor(CacheInterceptor())
            .build()

    @Provides
    @Singleton
    fun provideNetworkService(
        @BaseUrl baseUrl: String,
        httpClient: OkHttpClient,
        converterFactory: GsonConverterFactory
    ): NetworkService =
        Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(httpClient)
            .addConverterFactory(converterFactory)
            .build()
            .create(NetworkService::class.java)

    @Provides
    @Singleton
    fun provideDispatcherProvider(): DispatcherProvider =
        DefaultDispatcherProvider()
}