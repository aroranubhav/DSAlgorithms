package com.almax.dsalgorithms.di.component

import com.almax.dsalgorithms.di.ActivityScope
import com.almax.dsalgorithms.di.module.CategoryModule
import com.almax.dsalgorithms.presentation.category.CategoryActivity
import dagger.Component

@ActivityScope
@Component(
    dependencies = [ApplicationComponent::class],
    modules = [CategoryModule::class]
)
interface CategoryComponent {

    fun inject(activity: CategoryActivity)
}