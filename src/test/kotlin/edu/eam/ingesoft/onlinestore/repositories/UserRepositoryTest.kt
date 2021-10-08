package edu.eam.ingesoft.onlinestore.repositories

import edu.eam.ingesoft.onlinestore.model.City
import edu.eam.ingesoft.onlinestore.model.User
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.transaction.annotation.Transactional
import javax.persistence.EntityManager

@SpringBootTest
@Transactional

class UserRepositoryTest {

    @Autowired
    lateinit var entityManager: EntityManager

    @Autowired
    lateinit var userRepository: UserRepository

    @Test
    fun createTest(){
        val city= City(15L,"Armenia")
        entityManager.persist(city)
        userRepository.create(User("1","Cra 22","Juan","Torres",city))
        val user= entityManager.find(User::class.java,"1")
        Assertions.assertNotNull(user)
        Assertions.assertEquals("Cra 22",user.address)
        Assertions.assertEquals("Juan",user.name)
        Assertions.assertEquals("Torres",user.lastName)
        Assertions.assertEquals(15L,user.city.id)
        Assertions.assertEquals("Armenia",user.city.name)

    }
    @Test
    fun findTest(){
        val city= City(15L,"Armenia")
        entityManager.persist(city)
        entityManager.persist(User("1","Cra 22","Juan","Torres",city))
        val user= userRepository.find("1")
        Assertions.assertNotNull(user)
        Assertions.assertEquals("Cra 22",user?.address)
        Assertions.assertEquals("Juan",user?.name)
        Assertions.assertEquals("Torres",user?.lastName)
        Assertions.assertEquals(15L,user?.city?.id)
        Assertions.assertEquals("Armenia",user?.city?.name)
    }

    @Test
    fun updateTest(){
        val city= City(15L,"Armenia")
        entityManager.persist(city)
        entityManager.persist(User("1","Cra 22","Juan","Torres",city))
        entityManager.flush()

        val user= entityManager.find(User::class.java,"1")

        user.name= "Julian"
        user.lastName="Sanchez"
        user.address="Cra 23"

        entityManager.clear()
        userRepository.update(user)
        val userAssert= entityManager.find(User::class.java,"1")
        Assertions.assertEquals("Julian",userAssert.name)
        Assertions.assertEquals("Cra 23",user.address)
        Assertions.assertEquals("Sanchez",userAssert.lastName)


    }

    @Test
    fun deleteTest(){
        val city= City(15L,"Armenia")
        entityManager.persist(city)
        entityManager.persist(User("1","Cra 22","Juan","Torres",city))

        userRepository.delete("1")

        val user= entityManager.find(User::class.java,"1")

        Assertions.assertNull(user)

    }
}