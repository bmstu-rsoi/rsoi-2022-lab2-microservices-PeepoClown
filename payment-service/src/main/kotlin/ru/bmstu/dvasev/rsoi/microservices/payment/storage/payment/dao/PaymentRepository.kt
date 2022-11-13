package ru.bmstu.dvasev.rsoi.microservices.payment.storage.payment.dao

import org.springframework.data.jpa.repository.JpaRepository
import ru.bmstu.dvasev.rsoi.microservices.payment.storage.payment.entity.Payment
import java.util.Optional
import java.util.UUID

interface PaymentRepository: JpaRepository<Payment, Long> {

    fun findByPaymentUid(paymentUid: UUID): Optional<Payment>
}
