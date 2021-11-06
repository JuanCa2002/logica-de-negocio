package edu.eam.ingesoft.onlinestore.services

import edu.eam.ingesoft.onlinestore.exceptions.BusinessException
import edu.eam.ingesoft.onlinestore.model.entities.User
import edu.eam.ingesoft.onlinestore.repositories.CityRepository
import edu.eam.ingesoft.onlinestore.repositories.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class UserService {

    @Autowired
    lateinit var userRepository: UserRepository

    @Autowired
    lateinit var cityRepository: CityRepository

    fun createUser(user: User,idCity:Long){
        val city= cityRepository.find(idCity)

        if(city==null){
            throw BusinessException("This city does not exist")
        }
        val userById= userRepository.find(user.id)

        if (userById!=null){
            throw BusinessException("this user already exist")
        }
        user.city= city
        userRepository.create(user)
    }

    fun editUser(user: User,idUser:String) {
        val userById = userRepository.find(idUser)

        if (userById == null) {
            throw BusinessException("This user does not exist")
        }
        user.city= userById.city
        userRepository.update(user)
    }
}