package com.almax.dsalgorithms.data.remote

import okhttp3.Interceptor
import okhttp3.Response

class AuthenticationInterceptor : Interceptor {

    companion object {
        const val API_TOKEN =
            ""
    }

    override fun intercept(chain: Interceptor.Chain): Response {
        val response = chain.proceed(chain.request())
        return response
            .newBuilder()
            .addHeader(
                "Authorization",
                "Bearer $API_TOKEN"
            )
            .build()
    }
}