package com.almax.dsalgorithms.domain.usecase

import com.almax.dsalgorithms.domain.model.Category
import com.almax.dsalgorithms.domain.repository.CategoryRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class CategoryUseCase @Inject constructor(
    private val repository: CategoryRepository
) {

    operator fun invoke(): Flow<List<Category>> =
        repository.getCategories()
}