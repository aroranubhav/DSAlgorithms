package com.almax.dsalgorithms.domain.model

data class CategoryDto(
    val category: Category = Category(),
    val properties: ProblemProperties = ProblemProperties()
)
