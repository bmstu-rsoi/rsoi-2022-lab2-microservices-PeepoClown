package ru.bmstu.dvasev.rsoi.microservices.cars.storage.car

import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Pageable.unpaged
import org.springframework.stereotype.Service
import ru.bmstu.dvasev.rsoi.microservices.cars.model.CarModel
import ru.bmstu.dvasev.rsoi.microservices.cars.model.GetCarsRq
import ru.bmstu.dvasev.rsoi.microservices.cars.storage.car.dao.CarRepository
import ru.bmstu.dvasev.rsoi.microservices.cars.storage.car.entity.Car
import ru.bmstu.dvasev.rsoi.microservices.cars.storage.car.model.PageableCarsModel
import java.util.Objects.nonNull

@Service
class CarService(
    private val carRepository: CarRepository
) {

    fun getCarsPageable(getCarsRq: GetCarsRq): PageableCarsModel {
        var pageRequest: Pageable = unpaged()
        if (nonNull(getCarsRq.page) && nonNull(getCarsRq.size)) {
            pageRequest = PageRequest.of(getCarsRq.page!!, getCarsRq.size!!)
        }

        val cars = if (nonNull(getCarsRq.showAll)) {
            if (getCarsRq.showAll!!)
                carRepository.findAll(pageRequest).toList()
            else carRepository.findByAvailabilityTrue(pageRequest)
        } else carRepository.findByAvailabilityTrue(pageRequest)

        val carsList = cars.map(this::toCarModel)
        return PageableCarsModel(
            cars = carsList,
            page = if (pageRequest.isPaged) pageRequest.pageNumber + 1 else 1,
            pageSize = carsList.size
        )
    }

    private fun toCarModel(car: Car) =
        CarModel(
            carUid = car.carUid.toString(),
            brand = car.brand!!,
            model = car.model!!,
            registrationNumber = car.registrationNumber!!,
            power = car.power,
            type = car.type!!,
            price = car.price!!,
            available = car.availability!!
        )
}
