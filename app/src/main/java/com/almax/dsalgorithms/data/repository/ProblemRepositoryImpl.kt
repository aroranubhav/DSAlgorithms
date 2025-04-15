package com.almax.dsalgorithms.data.repository

import com.almax.dsalgorithms.data.remote.FilesNetworkService
import com.almax.dsalgorithms.data.remote.FoldersNetworkService
import com.almax.dsalgorithms.domain.model.Category
import com.almax.dsalgorithms.domain.model.ProblemProperties
import com.almax.dsalgorithms.domain.repository.ProblemRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class ProblemRepositoryImpl @Inject constructor(
    private val folderNetworkService: FoldersNetworkService,
    private val filesNetworkService: FilesNetworkService
) : ProblemRepository {

    override fun getProblems(category: String): Flow<List<Category>> {
        return flow {
            emit(folderNetworkService.getProblems(category))
        }
    }

    override fun getProblemProperties(filePath: String): Flow<ProblemProperties> {
        return flow {
            emit(filesNetworkService.getProblemProperties(filePath))
        }
    }
}