package edu.eam.ingesoft.onlinestore.repositories

import edu.eam.ingesoft.onlinestore.model.City
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.transaction.annotation.Transactional
import javax.persistence.EntityManager


@SpringBootTest
@Transactional
class CityRepositoryTest {

    @Autowired
    lateinit var entityManager: EntityManager

    @Autowired
    lateinit var cityRepository: CityRepository

    @Test
    fun createTest(){
        cityRepository.create(City(15L,"Armenia"))
        val city= entityManager.find(City::class.java,15L)
        Assertions.assertNotNull(city)
        Assertions.assertEquals("Armenia",city.name)

    }
    @Test
    fun findTest(){
        entityManager.persist(City(15L,"Armenia"))

        val city= cityRepository.find(15L)

        Assertions.assertNotNull(city)
        Assertions.assertEquals("Armenia",city?.name)
    }

    @Test
    fun updateTest(){
        entityManager.persist(City(15L,"Armenia"))
        entityManager.flush()

        val city= entityManager.find(City::class.java,15L)
        city.name= "Cali"

        entityManager.clear()
        cityRepository.update(city)
        val cityAssert= entityManager.find(City::class.java,15L)
        Assertions.assertEquals("Cali",cityAssert.name)


    }

    @Test
    fun deleteTest(){
        entityManager.persist(City(15L,"Armenia"))

        cityRepository.delete(15L)

        val city= entityManager.find(City::class.java,15L)

        Assertions.assertNull(city)

    }


}