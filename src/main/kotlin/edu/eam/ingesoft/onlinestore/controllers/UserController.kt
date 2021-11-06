package edu.eam.ingesoft.onlinestore.controllers

import edu.eam.ingesoft.onlinestore.model.entities.User
import edu.eam.ingesoft.onlinestore.services.UserService

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*

@RestController


@RequestMapping("/users")
class UserController {

    @Autowired
    lateinit var userService: UserService

    @PostMapping("/cities/{id}")
    fun createUser(@PathVariable("id") idCity:Long,@RequestBody user:User){
        userService.createUser(user,idCity)
    }

    @PutMapping("{id}")
    fun editUser(@PathVariable("id") idUser:String,@RequestBody user: User){
        user.id= idUser
        userService.editUser(user,idUser)
    }

}