package com.almax.dsalgorithms.domain.repository

import com.almax.dsalgorithms.domain.model.Category
import kotlinx.coroutines.flow.Flow

interface CategoryRepository {

    fun getCategories(): Flow<List<Category>>
}