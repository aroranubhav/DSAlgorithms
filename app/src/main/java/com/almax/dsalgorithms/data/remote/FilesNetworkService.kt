package com.almax.dsalgorithms.data.remote

import com.almax.dsalgorithms.domain.model.ProblemProperties
import retrofit2.http.GET
import retrofit2.http.Path

interface FilesNetworkService {

    @GET("{file_path}")
    suspend fun getProblemProperties(
        @Path("file_path") path: String
    ): ProblemProperties
}