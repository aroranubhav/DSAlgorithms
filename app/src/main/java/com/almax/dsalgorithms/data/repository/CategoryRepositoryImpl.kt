package com.almax.dsalgorithms.data.repository

import com.almax.dsalgorithms.data.remote.FoldersNetworkService
import com.almax.dsalgorithms.domain.model.Category
import com.almax.dsalgorithms.domain.repository.CategoryRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class CategoryRepositoryImpl @Inject constructor(
    private val networkService: FoldersNetworkService
) : CategoryRepository {

    override fun getCategories(): Flow<List<Category>> {
        return flow {
            emit(networkService.getCategories())
        }
    }
}