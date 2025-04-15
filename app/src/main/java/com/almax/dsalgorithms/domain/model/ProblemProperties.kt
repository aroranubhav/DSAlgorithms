package com.almax.dsalgorithms.domain.model

import com.almax.dsalgorithms.util.AppConstants.UNKNOWN
import com.google.gson.annotations.SerializedName

data class ProblemProperties(
    @SerializedName("problem_name")
    val problemName: String = "",
    @SerializedName("problem_lc_link")
    val problemLcLink: String = "",
    @SerializedName("problem_lc_number")
    val problemLcNumber: Int = -1,
    @SerializedName("problem_lc_level")
    val problemLcLevel: String = UNKNOWN,
    @SerializedName("asked_in_companies")
    val askedInCompanies: List<String> = arrayListOf()
)
