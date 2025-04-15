package com.almax.dsalgorithms.domain.model

import com.almax.dsalgorithms.util.AppConstants.DIR_TYPE
import com.google.gson.annotations.SerializedName

data class Category(
    val name: String = "",
    val path: String = "",
    @SerializedName("html_url")
    val url: String = "",
    val type: String = DIR_TYPE
)

fun Category.toCategoryDto(
    problemName: String, problemLcLink: String, problemLcNumber: Int, problemLcLevel: String,
    askedInCompanies: List<String>
): CategoryDto {
    val problemProperty = ProblemProperties(
        problemName,
        problemLcLink,
        problemLcNumber,
        problemLcLevel,
        askedInCompanies
    )
    return CategoryDto(
        this,
        problemProperty
    )
}
