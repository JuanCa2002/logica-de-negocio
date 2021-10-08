package edu.eam.ingesoft.onlinestore.services

import edu.eam.ingesoft.onlinestore.exceptions.BusinessException
import edu.eam.ingesoft.onlinestore.model.User
import edu.eam.ingesoft.onlinestore.repositories.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class UserService {

    @Autowired
    lateinit var userRepository: UserRepository

    fun createUser(user: User){
        val userById= userRepository.find(user.id)

        if (userById!=null){
            throw BusinessException("this user already exist")
        }

        userRepository.create(user)
    }

    fun editStore(user: User) {
        val userById = userRepository.find(user.id)

        if (userById == null) {
            throw BusinessException("This user does not exist")
        }

        userRepository.update(user)
    }
}