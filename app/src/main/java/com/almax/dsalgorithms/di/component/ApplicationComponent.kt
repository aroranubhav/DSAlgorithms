package com.almax.dsalgorithms.di.component

import com.almax.dsalgorithms.MainApplication
import com.almax.dsalgorithms.data.remote.FilesNetworkService
import com.almax.dsalgorithms.data.remote.FoldersNetworkService
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

    fun getFoldersNetworkService(): FoldersNetworkService

    fun getFilesNetworkService(): FilesNetworkService

    fun getDispatcherProvider(): DispatcherProvider
}