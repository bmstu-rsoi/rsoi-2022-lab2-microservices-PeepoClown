package ru.bmstu.dvasev.rsoi.microservices.gateway.configuration

import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Configuration
import ru.bmstu.dvasev.rsoi.microservices.gateway.configuration.rest.properties.CarsRestProperties

@Configuration
@EnableConfigurationProperties(
    CarsRestProperties::class
)
class GatewayServicePropertiesConfiguration
