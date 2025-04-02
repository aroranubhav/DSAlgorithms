package com.almax.dsalgorithms.di.module

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.almax.dsalgorithms.data.remote.NetworkService
import com.almax.dsalgorithms.data.repository.CategoryRepositoryImpl
import com.almax.dsalgorithms.di.ActivityContext
import com.almax.dsalgorithms.di.ActivityScope
import com.almax.dsalgorithms.domain.repository.CategoryRepository
import com.almax.dsalgorithms.domain.usecase.CategoryUseCase
import com.almax.dsalgorithms.presentation.base.ViewModelProviderFactory
import com.almax.dsalgorithms.presentation.category.CategoryAdapter
import com.almax.dsalgorithms.presentation.category.CategoryViewModel
import com.almax.dsalgorithms.util.DispatcherProvider
import dagger.Module
import dagger.Provides

@Module
class CategoryModule(
    private val activity: AppCompatActivity
) {

    @ActivityContext
    @Provides
    fun provideContext(): Context =
        activity

    @Provides
    @ActivityScope
    fun provideCategoryRepository(
        networkService: NetworkService
    ): CategoryRepository =
        CategoryRepositoryImpl(networkService)

    @Provides
    fun provideCategoryViewModel(
        useCase: CategoryUseCase,
        dispatcherProvider: DispatcherProvider
    ): CategoryViewModel =
        ViewModelProvider(
            activity,
            ViewModelProviderFactory(CategoryViewModel::class) {
                CategoryViewModel(
                    useCase, dispatcherProvider
                )
            })[CategoryViewModel::class]


    @Provides
    fun provideCategoryAdapter(): CategoryAdapter =
        CategoryAdapter(arrayListOf())
}