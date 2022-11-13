package ru.bmstu.dvasev.rsoi.microservices.rental.model

import java.time.LocalDate

data class RentalModel(
    val rentalUid: String,
    val username: String,
    val paymentUid: String,
    val carUid: String,
    val dateFrom: LocalDate,
    val dateTo: LocalDate,
    val status: RentStatus
)
