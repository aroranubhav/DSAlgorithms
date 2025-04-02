package com.almax.dsalgorithms

import android.app.Application
import com.almax.dsalgorithms.di.component.ApplicationComponent
import com.almax.dsalgorithms.di.component.DaggerApplicationComponent
import com.almax.dsalgorithms.di.module.ApplicationModule

class MainApplication : Application() {

    lateinit var component: ApplicationComponent

    override fun onCreate() {
        super.onCreate()
        injectDependencies()
    }

    private fun injectDependencies() {
        component = DaggerApplicationComponent
            .builder()
            .applicationModule(ApplicationModule(this@MainApplication))
            .build()
        component.inject(this@MainApplication)
    }
}