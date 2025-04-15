package com.almax.dsalgorithms.di.component

import com.almax.dsalgorithms.di.ActivityScope
import com.almax.dsalgorithms.di.module.ProblemModule
import com.almax.dsalgorithms.presentation.problem.ProblemActivity
import dagger.Component

@ActivityScope
@Component(
    dependencies = [ApplicationComponent::class],
    modules = [ProblemModule::class]
)
interface ProblemComponent {

    fun inject(activity: ProblemActivity)
}