package ru.bmstu.dvasev.rsoi.microservices.gateway.api.v1

import mu.KotlinLogging
import org.springframework.http.MediaType.APPLICATION_JSON_VALUE
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import ru.bmstu.dvasev.rsoi.microservices.cars.model.GetCarsRq
import ru.bmstu.dvasev.rsoi.microservices.gateway.action.CarsGetAction
import ru.bmstu.dvasev.rsoi.microservices.gateway.action.RentCarAction
import ru.bmstu.dvasev.rsoi.microservices.gateway.model.CreateRentalRequest
import ru.bmstu.dvasev.rsoi.microservices.gateway.model.CreateRentalRequestWithUser
import javax.validation.Valid

@RestController
@RequestMapping(
    path = ["api/v1"]
)
class ApiV1Controller(
    private val carsGetAction: CarsGetAction,
    private val rentCarAction: RentCarAction
) {

    private val log = KotlinLogging.logger {}

    @GetMapping(
        path = ["cars"],
        produces = [APPLICATION_JSON_VALUE]
    )
    fun getAvailableCars(
        @RequestParam("page") page: Int?,
        @RequestParam("size") size: Int?,
        @RequestParam("showAll") showAll: Boolean?
    ): ResponseEntity<*> {
        val request = GetCarsRq(
            page = page?.minus(1),
            size = size,
            showAll = showAll
        )
        log.debug { "Received new get available cars request. $request" }
        val response = carsGetAction.process(request)
        log.debug { "Return get available cars response. $response" }
        return response
    }

    @PostMapping(
        path = ["rental"],
        consumes = [APPLICATION_JSON_VALUE],
        produces = [APPLICATION_JSON_VALUE]
    )
    fun createCarRent(
        @RequestHeader("X-User-Name") username: String,
        @Valid @RequestBody request: CreateRentalRequest
    ): ResponseEntity<*> {
        val requestWithUser = CreateRentalRequestWithUser(
            carUid = request.carUid!!,
            dateFrom = request.dateFrom!!,
            dateTo = request.dateTo!!,
            username = username
        )
        log.debug { "Received new create car rent request. $requestWithUser" }
        val response = rentCarAction.process(requestWithUser)
        log.debug { "Return rent car response. $response" }
        return response
    }
}
