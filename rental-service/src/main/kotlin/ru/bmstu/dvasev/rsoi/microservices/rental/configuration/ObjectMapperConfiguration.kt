package ru.bmstu.dvasev.rsoi.microservices.rental.configuration

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.SerializationFeature.WRITE_DATES_AS_TIMESTAMPS
import com.fasterxml.jackson.databind.module.SimpleModule
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class ObjectMapperConfiguration {

    @Bean
    fun objectMapper(): ObjectMapper {
        val customDeserializationModule = SimpleModule()
        return ObjectMapper()
            .registerModule(customDeserializationModule)
            .registerModule(JavaTimeModule())
            .configure(WRITE_DATES_AS_TIMESTAMPS, false)
    }
}
