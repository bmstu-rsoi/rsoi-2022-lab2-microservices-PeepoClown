package ru.bmstu.dvasev.rsoi.microservices.common.model

data class ErrorResponse(
    override val message: String
) : Error
