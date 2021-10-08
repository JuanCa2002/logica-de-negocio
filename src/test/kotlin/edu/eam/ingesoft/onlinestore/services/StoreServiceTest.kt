package edu.eam.ingesoft.onlinestore.services

import edu.eam.ingesoft.onlinestore.exceptions.BusinessException
import edu.eam.ingesoft.onlinestore.model.City
import edu.eam.ingesoft.onlinestore.model.Store
import edu.eam.ingesoft.onlinestore.model.User
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.transaction.annotation.Transactional
import javax.persistence.EntityManager


@SpringBootTest
@Transactional
class StoreServiceTest {

    @Autowired
    lateinit var storeService: StoreService

    @Autowired
    lateinit var entityManager: EntityManager

    @Test
    fun createStoreAlreadyExistTest(){
        val city= City(15L,"Armenia")
        entityManager.persist(city)
        entityManager.persist(Store(16L,"Cra 15","Tienda mascotas",city))

        val newStore= Store(16L,"Cra 15","Tienda mascotas",city)

        try {
            storeService.createStore(newStore)
            Assertions.fail()

        }catch (e: BusinessException) {
            Assertions.assertEquals("this store already exist", e.message)
        }

    }

    @Test
    fun createStoreHappyPathTest(){
        val city= City(15L,"Armenia")
        entityManager.persist(city)
        entityManager.persist(Store(16L,"Cra 15","Tienda mascotas",city))

        val storeAssert=entityManager.find(Store::class.java,16L)
        Assertions.assertNotNull(storeAssert)

        Assertions.assertEquals("Cra 15",storeAssert.address)
        Assertions.assertEquals("Tienda mascotas",storeAssert.name)

    }

    @Test
    fun editStoreNotExistTest(){
        val city= City(15L,"Armenia")
        entityManager.persist(city)
        val store=Store(16L,"Cra 15","Tienda mascotas",city)

        try {
            storeService.editStore(store)
            Assertions.fail()

        }catch (e: BusinessException) {
            Assertions.assertEquals("This store does not exist", e.message)
        }

    }

    @Test
    fun editStoreHappyPathTest(){
        val city= City(15L,"Armenia")
        entityManager.persist(city)
        entityManager.persist(Store(16L,"Cra 15","Tienda mascotas",city))

        val storeUpdate= (Store(16L,"Cra 17","Tienda videojuegos",city))
        storeService.editStore(storeUpdate)

        val storeUpdateAssert= entityManager.find(Store::class.java,16L)
        Assertions.assertEquals("Cra 17",storeUpdateAssert.address)
        Assertions.assertEquals("Tienda videojuegos",storeUpdateAssert.name)


    }

}