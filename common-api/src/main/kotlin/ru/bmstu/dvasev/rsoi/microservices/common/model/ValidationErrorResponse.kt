package ru.bmstu.dvasev.rsoi.microservices.common.model

data class ValidationErrorDescription(
    val field: String,
    val error: String
)

data class ValidationErrorResponse(
    override val message: String,
    val errors: List<ValidationErrorDescription>
) : Error
