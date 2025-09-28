package com.prado.eduardo.luiz.newsapp.data.util

import com.prado.eduardo.luiz.domain.util.ErrorMapper
import com.prado.eduardo.luiz.newsapp.data.remote.dto.ApiErrorDTO
import com.squareup.moshi.Moshi
import retrofit2.HttpException
import java.io.IOException

class ErrorMapperImpl : ErrorMapper {
    private val moshi: Moshi = Moshi.Builder().build()

    override fun map(throwable: Throwable): String = when (throwable) {
        is IOException -> "Please check your internet connection."
        is HttpException -> when (throwable.code()) {
            400 -> "Invalid request."
            401 -> {
                val errorJson = throwable.response()?.errorBody()?.string()
                val errorMessage = parseErrorMoshi(errorJson)?.message
                if (errorMessage?.contains("API key", ignoreCase = true) == true) {
                    "API key not configured. Please set your API key in the local.properties file." +
                            "using the key 'NEWS_API_KEY'."
                } else {
                    "Unauthorized access."
                }
            }

            403 -> "Forbidden access."
            404 -> "Resource not found."
            500 -> "Internal server error."
            else -> "Unknown server error (${throwable.code()})."
        }

        else -> {
            val message = throwable.localizedMessage ?: "An unexpected error occurred."
            if (message.contains("API key", ignoreCase = true)) {
                "API key not configured. Please set your API key in the local.properties file."
            } else {
                message
            }
        }
    }

    private fun parseErrorMoshi(rawJson: String?): ApiErrorDTO? {
        if (rawJson.isNullOrBlank()) return null
        return try {
            val adapter = moshi.adapter(ApiErrorDTO::class.java)
            adapter.fromJson(rawJson)
        } catch (_: Exception) {
            null
        }
    }
}
