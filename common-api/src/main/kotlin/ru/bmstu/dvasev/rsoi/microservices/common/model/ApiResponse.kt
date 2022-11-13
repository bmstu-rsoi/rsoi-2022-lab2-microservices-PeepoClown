package ru.bmstu.dvasev.rsoi.microservices.common.model

import org.springframework.http.HttpStatus

data class ApiResponse<T>(
    val httpCode: HttpStatus,
    val response: T? = null,
    val error: Error? = null
)
