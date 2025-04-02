package com.almax.dsalgorithms.domain.model

import com.almax.dsalgorithms.util.AppConstants.FILE_TYPE
import com.google.gson.annotations.SerializedName

data class Problem(
    val name: String = "",
    val path: String = "",
    @SerializedName("html_url")
    val url: String = "",
    @SerializedName("download_url")
    val downloadUrl: String = "",
    val type: String = FILE_TYPE
)
