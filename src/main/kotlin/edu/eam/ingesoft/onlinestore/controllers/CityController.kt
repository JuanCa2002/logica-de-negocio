package edu.eam.ingesoft.onlinestore.controllers

import edu.eam.ingesoft.onlinestore.model.entities.City
import edu.eam.ingesoft.onlinestore.services.CityService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*

@RestController

@RequestMapping("/cities")
class CityController {

    @Autowired
    lateinit var cityService: CityService

    @PostMapping
    fun createCity(@RequestBody city: City){
        cityService.createCity(city)
    }
}