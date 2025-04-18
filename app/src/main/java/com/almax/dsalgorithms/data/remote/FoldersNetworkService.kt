package com.almax.dsalgorithms.data.remote

import com.almax.dsalgorithms.domain.model.CategoryResponse
import com.almax.dsalgorithms.domain.model.ProblemProperties
import com.almax.dsalgorithms.domain.model.ProblemResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface FoldersNetworkService {

    @GET("contents")
    suspend fun getCategories(): CategoryResponse

    @GET("contents/{questions_path}")
    suspend fun getProblems(
        @Path("questions_path") path: String
    ): CategoryResponse

    @GET("contents/{solutions_path}")
    suspend fun getSolutions(
        @Query("solutions_path") path: String
    ): ProblemResponse
}