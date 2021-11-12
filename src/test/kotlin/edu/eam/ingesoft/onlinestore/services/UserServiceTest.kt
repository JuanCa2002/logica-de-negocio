package edu.eam.ingesoft.onlinestore.services

import edu.eam.ingesoft.onlinestore.exceptions.BusinessException
import edu.eam.ingesoft.onlinestore.model.entities.City
import edu.eam.ingesoft.onlinestore.model.entities.User
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.transaction.annotation.Transactional
import javax.persistence.EntityManager


@SpringBootTest
@Transactional
class UserServiceTest {

    @Autowired
    lateinit var userService: UserService

    @Autowired
    lateinit var entityManager: EntityManager

    @Test
    fun createUserAlreadyExistTest(){
        val city= City(15L,"Armenia")
        entityManager.persist(city)
        entityManager.persist(User("1","Cra 22","Juan","Torres",city))

        val newUser= User("1","Cra 22","Juan","Torres",city)

        try {
            userService.createUser(newUser,15L)
            Assertions.fail()

        }catch (e: BusinessException) {
            Assertions.assertEquals("this user already exist", e.message)
        }

    }
    @Test
    fun createUserHappyPathTest(){
        val city= City(15L,"Armenia")
        entityManager.persist(city)
        val user= User("1","Cra 22","Juan","Torres",city)

        userService.createUser(user,15L)

        val userAssert=entityManager.find(User::class.java,"1")
        Assertions.assertNotNull(userAssert)

        Assertions.assertEquals("Cra 22",userAssert.address)
        Assertions.assertEquals("Juan",userAssert.name)
        Assertions.assertEquals("Torres",userAssert.lastName)
    }

    @Test
    fun editUserNotExistTest(){
        val city= City(15L,"Armenia")
        entityManager.persist(city)
        val user= User("1","Cra 22","Juan","Torres",city)

        try {
            userService.editUser(user,"1")
            Assertions.fail()

        }catch (e: BusinessException) {
            Assertions.assertEquals("This user does not exist", e.message)
        }

    }

    @Test
    fun editUserHappyPathTest(){
        val city= City(15L,"Armenia")
        entityManager.persist(city)
        entityManager.persist(User("1","Cra 22","Juan","Torres",city))

        val userUpdate= User("1","Cra 22","Julian","Beltran",city)
        userService.editUser(userUpdate,"1")

        val userUpdateAssert= entityManager.find(User::class.java,"1")
        Assertions.assertEquals("Julian",userUpdateAssert.name)
        Assertions.assertEquals("Beltran",userUpdateAssert.lastName)


    }
}