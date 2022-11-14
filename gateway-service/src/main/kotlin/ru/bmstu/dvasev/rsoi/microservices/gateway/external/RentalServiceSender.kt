package ru.bmstu.dvasev.rsoi.microservices.gateway.external

import mu.KotlinLogging
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.stereotype.Service
import org.springframework.web.client.RestTemplate
import ru.bmstu.dvasev.rsoi.microservices.common.model.ApiResponse
import ru.bmstu.dvasev.rsoi.microservices.common.model.ErrorResponse
import ru.bmstu.dvasev.rsoi.microservices.gateway.configuration.rest.properties.RentalRestProperties
import ru.bmstu.dvasev.rsoi.microservices.rental.model.CreateRentRq
import ru.bmstu.dvasev.rsoi.microservices.rental.model.RentalModel
import java.nio.charset.StandardCharsets

class WrappedCreateRentRs(httpCode: HttpStatus) : ApiResponse<RentalModel>(httpCode)

@Service
class RentalServiceSender(
    private val rentalRestTemplate: RestTemplate,
    private val rentalRestProperties: RentalRestProperties
) {

    private val log = KotlinLogging.logger {}

    fun createRental(createRentRq: CreateRentRq): ApiResponse<RentalModel> {
        val request = HttpEntity(createRentRq, buildHeaders())
        log.debug { "Sending create rent request. $request" }

        return try {
            val response = rentalRestTemplate.postForObject(
                rentalRestProperties.createRentalPath,
                request,
                WrappedCreateRentRs::class.java
            )
            log.info { "Successfully received response from create rent service. $response" }
            response!!
        } catch (ex: Exception) {
            log.warn(ex) { "Failed to send create rent request. ${ex.message}" }
            ApiResponse(
                httpCode = HttpStatus.INTERNAL_SERVER_ERROR,
                error = ErrorResponse("Failed to send create rent request. ${ex.message}")
            )
        }
    }

    private fun buildHeaders(): HttpHeaders {
        val headers = HttpHeaders()
        headers.contentType = MediaType("application", "json", StandardCharsets.UTF_8)
        return headers
    }
}