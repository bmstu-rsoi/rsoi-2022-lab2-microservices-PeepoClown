package ru.bmstu.dvasev.rsoi.microservices.gateway.external

import mu.KotlinLogging
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR
import org.springframework.http.MediaType
import org.springframework.stereotype.Service
import org.springframework.web.client.RestTemplate
import ru.bmstu.dvasev.rsoi.microservices.cars.model.GetCarsRq
import ru.bmstu.dvasev.rsoi.microservices.cars.model.GetCarsRs
import ru.bmstu.dvasev.rsoi.microservices.common.model.ApiResponse
import ru.bmstu.dvasev.rsoi.microservices.common.model.ErrorResponse
import ru.bmstu.dvasev.rsoi.microservices.gateway.configuration.rest.properties.CarsRestProperties
import java.nio.charset.StandardCharsets.UTF_8

class WrappedGetCarsRs(httpCode: HttpStatus) : ApiResponse<GetCarsRs>(httpCode)

@Service
class CarsServiceSender(
    private val carsRestTemplate: RestTemplate,
    private val carsRestProperties: CarsRestProperties
) {

    private val log = KotlinLogging.logger {}

    fun getAvailableCars(getCarsRq: GetCarsRq): ApiResponse<GetCarsRs> {
        val request = HttpEntity(getCarsRq, buildHeaders())
        log.debug { "Sending get available cars request. $request" }

        try {
            val response = carsRestTemplate.postForObject(carsRestProperties.getCarsPath, request, WrappedGetCarsRs::class.java)
            log.info { "Successfully received response from get available cars service. $response" }
            return response!!
        } catch (ex: Exception) {
            log.warn(ex) { "Failed to send get available cars request. ${ex.message}" }
            return ApiResponse(
                httpCode = INTERNAL_SERVER_ERROR,
                error = ErrorResponse("Failed to send get available cars request. ${ex.message}")
            )
        }
    }

    private fun buildHeaders(): HttpHeaders {
        val headers = HttpHeaders()
        headers.contentType = MediaType("application", "json", UTF_8)
        return headers
    }
}
