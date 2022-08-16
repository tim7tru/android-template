package com.timmytruong.template.data.model

sealed class Result {
    data class Success<T>(val data: T): Result()
    data class Error(val message: String): Result()
}
