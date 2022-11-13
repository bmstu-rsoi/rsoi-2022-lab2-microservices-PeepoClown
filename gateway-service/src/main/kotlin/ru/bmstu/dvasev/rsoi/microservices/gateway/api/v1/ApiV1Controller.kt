package ru.bmstu.dvasev.rsoi.microservices.gateway.api.v1

import mu.KotlinLogging
import org.springframework.http.MediaType.APPLICATION_JSON_VALUE
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping(
    path = ["api/v1"],
    produces = [APPLICATION_JSON_VALUE]
)
class ApiV1Controller {

    private val log = KotlinLogging.logger {}

    @GetMapping("cars")
    fun getCars(@RequestParam("page") page: Int?,
                @RequestParam("size") size: Int?,
                @RequestParam("showAll") showAll: Boolean?) {
        log.debug { "Received new get cars request. " }
    }
}