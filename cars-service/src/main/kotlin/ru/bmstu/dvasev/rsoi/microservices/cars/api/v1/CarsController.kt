package ru.bmstu.dvasev.rsoi.microservices.cars.api.v1

import mu.KotlinLogging
import org.springframework.http.HttpStatus.OK
import org.springframework.http.MediaType.APPLICATION_JSON_VALUE
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import ru.bmstu.dvasev.rsoi.microservices.cars.model.GetCarsRq
import ru.bmstu.dvasev.rsoi.microservices.cars.model.GetCarsRs
import ru.bmstu.dvasev.rsoi.microservices.cars.storage.car.CarService
import ru.bmstu.dvasev.rsoi.microservices.common.model.ApiResponse
import javax.validation.Valid

@RestController
@RequestMapping(
    path = ["api/v1/cars"],
    consumes = [APPLICATION_JSON_VALUE],
    produces = [APPLICATION_JSON_VALUE]
)
class CarsController(
    private val carService: CarService
) {

    private val log = KotlinLogging.logger {}

    @PostMapping("get/pageable")
    fun getCarsPagination(@Valid @RequestBody request: GetCarsRq): ApiResponse<GetCarsRs> {
        log.debug { "Received new get cars pageable request. $request" }
        val cars = carService.getCarsPageable(request)
        val response = ApiResponse(
            httpCode = OK,
            response = GetCarsRs(
                page = request.page,
                pageSize = request.size,
                totalElements = cars.size,
                items = cars
            )
        )
        log.debug { "Return a get cars pageable response. $response" }
        return response
    }
}
