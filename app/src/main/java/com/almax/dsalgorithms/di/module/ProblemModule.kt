package com.almax.dsalgorithms.di.module

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.almax.dsalgorithms.data.remote.FilesNetworkService
import com.almax.dsalgorithms.data.remote.FoldersNetworkService
import com.almax.dsalgorithms.data.repository.ProblemRepositoryImpl
import com.almax.dsalgorithms.di.ActivityContext
import com.almax.dsalgorithms.di.ActivityScope
import com.almax.dsalgorithms.domain.repository.ProblemRepository
import com.almax.dsalgorithms.domain.usecase.ProblemUseCase
import com.almax.dsalgorithms.presentation.base.ViewModelProviderFactory
import com.almax.dsalgorithms.presentation.problem.ProblemAdapter
import com.almax.dsalgorithms.presentation.problem.ProblemViewModel
import com.almax.dsalgorithms.util.DispatcherProvider
import dagger.Module
import dagger.Provides

@Module
class ProblemModule(
    private val activity: AppCompatActivity
) {

    @ActivityContext
    @Provides
    fun provideContext(): Context =
        activity

    @Provides
    @ActivityScope
    fun provideProblemRepository(
        foldersNetworkService: FoldersNetworkService,
        filesNetworkService: FilesNetworkService
    ): ProblemRepository =
        ProblemRepositoryImpl(foldersNetworkService, filesNetworkService)

    @Provides
    fun provideProblemViewModel(
        dispatcherProvider: DispatcherProvider,
        useCase: ProblemUseCase
    ): ProblemViewModel =
        ViewModelProvider(
            activity,
            ViewModelProviderFactory(ProblemViewModel::class) {
                ProblemViewModel(
                    dispatcherProvider,
                    useCase
                )
            })[ProblemViewModel::class]

    @Provides
    fun provideProblemAdapter(): ProblemAdapter =
        ProblemAdapter(arrayListOf())
}