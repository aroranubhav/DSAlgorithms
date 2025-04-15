package com.almax.dsalgorithms.domain.repository

import com.almax.dsalgorithms.domain.model.Category
import com.almax.dsalgorithms.domain.model.ProblemProperties
import kotlinx.coroutines.flow.Flow

interface ProblemRepository {

    fun getProblems(category: String): Flow<List<Category>>

    fun getProblemProperties(filePath: String): Flow<ProblemProperties>
}