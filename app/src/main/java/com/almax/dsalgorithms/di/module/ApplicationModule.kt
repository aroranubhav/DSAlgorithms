package com.almax.dsalgorithms.di.module

import android.app.Application
import android.content.Context
import com.almax.dsalgorithms.data.remote.AuthenticationInterceptor
import com.almax.dsalgorithms.data.remote.CacheInterceptor
import com.almax.dsalgorithms.data.remote.FilesNetworkService
import com.almax.dsalgorithms.data.remote.FoldersNetworkService
import com.almax.dsalgorithms.di.ApplicationContext
import com.almax.dsalgorithms.di.FilesBaseUrl
import com.almax.dsalgorithms.di.FilesHttpClient
import com.almax.dsalgorithms.di.FoldersBaseUrl
import com.almax.dsalgorithms.di.FoldersHttpClient
import com.almax.dsalgorithms.util.DefaultDispatcherProvider
import com.almax.dsalgorithms.util.DispatcherProvider
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
class ApplicationModule(
    private val application: Application
) {

    @ApplicationContext
    @Provides
    fun provideContext(): Context =
        application

    @FoldersBaseUrl
    @Provides
    fun provideFoldersBaseUrl(): String =
        "https://api.github.com/repos/aroranubhav/Data-Structures-and-Algorithms/"

    @FilesBaseUrl
    @Provides
    fun provideFilesBaseUrl(): String =
        "https://raw.githubusercontent.com/aroranubhav/Data-Structures-and-Algorithms/main/"

    @Provides
    @Singleton
    fun provideGsonConverterFactory(): GsonConverterFactory =
        GsonConverterFactory.create()

    @Provides
    @Singleton
    fun provideAuthInterceptor(): AuthenticationInterceptor =
        AuthenticationInterceptor()

    @Provides
    @Singleton
    fun provideCacheInterceptor(): CacheInterceptor =
        CacheInterceptor()

    @FoldersHttpClient
    @Provides
    @Singleton
    fun provideFoldersHttpClient(
        authenticationInterceptor: AuthenticationInterceptor,
        cacheInterceptor: CacheInterceptor
    ): OkHttpClient =
        OkHttpClient()
            .newBuilder()
            .addInterceptor(authenticationInterceptor)
            /*.cache(Cache(File(application.cacheDir, "http-cache"), 10L * 1024L * 1024L))
            .addNetworkInterceptor(CacheInterceptor())*/
            .build()

    @FilesHttpClient
    @Provides
    @Singleton
    fun provideFilesHttpClient(
        authenticationInterceptor: AuthenticationInterceptor
    ): OkHttpClient =
        OkHttpClient()
            .newBuilder()
            .addInterceptor(authenticationInterceptor)
            /*.cache(Cache(File(application.cacheDir, "http-cache"), 10L * 1024L * 1024L))
            .addNetworkInterceptor(CacheInterceptor())*/
            .build()

    @Provides
    @Singleton
    fun provideFoldersNetworkService(
        @FoldersBaseUrl baseUrl: String,
        @FoldersHttpClient httpClient: OkHttpClient,
        converterFactory: GsonConverterFactory
    ): FoldersNetworkService =
        Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(httpClient)
            .addConverterFactory(converterFactory)
            .build()
            .create(FoldersNetworkService::class.java)

    @Provides
    @Singleton
    fun provideFilesNetworkService(
        @FilesBaseUrl baseUrl: String,
        @FilesHttpClient httpClient: OkHttpClient,
        converterFactory: GsonConverterFactory
    ): FilesNetworkService =
        Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(httpClient)
            .addConverterFactory(converterFactory)
            .build()
            .create(FilesNetworkService::class.java)

    @Provides
    @Singleton
    fun provideDispatcherProvider(): DispatcherProvider =
        DefaultDispatcherProvider()
}