package edu.eam.ingesoft.onlinestore.services

import edu.eam.ingesoft.onlinestore.exceptions.BusinessException
import edu.eam.ingesoft.onlinestore.model.entities.City
import edu.eam.ingesoft.onlinestore.repositories.CityRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class CityService {

    @Autowired
    lateinit var cityRepository: CityRepository

    fun createCity(city: City){
        val cityById= cityRepository.find(city.id)

        if(cityById!= null){
            throw BusinessException("This city already exist")
        }
        cityRepository.create(city)
    }
}