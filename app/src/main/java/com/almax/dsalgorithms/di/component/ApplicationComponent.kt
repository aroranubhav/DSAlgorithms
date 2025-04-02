package com.almax.dsalgorithms.di.component

import com.almax.dsalgorithms.MainApplication
import com.almax.dsalgorithms.data.remote.NetworkService
import com.almax.dsalgorithms.di.module.ApplicationModule
import com.almax.dsalgorithms.util.DispatcherProvider
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    modules = [ApplicationModule::class]
)
interface ApplicationComponent {

    fun inject(application: MainApplication)

    fun getNetworkService(): NetworkService

    fun getDispatcherProvider(): DispatcherProvider
}