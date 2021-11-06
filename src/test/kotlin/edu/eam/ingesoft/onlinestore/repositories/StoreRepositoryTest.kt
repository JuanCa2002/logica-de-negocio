package edu.eam.ingesoft.onlinestore.repositories


import edu.eam.ingesoft.onlinestore.model.entities.City
import edu.eam.ingesoft.onlinestore.model.entities.Store
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.transaction.annotation.Transactional
import javax.persistence.EntityManager

@SpringBootTest
@Transactional
class StoreRepositoryTest {

    @Autowired
    lateinit var entityManager: EntityManager

    @Autowired
    lateinit var storeRepository: StoreRepository

    @Test
    fun createTest(){
        val city= City(15L,"Armenia")
        entityManager.persist(city)
        storeRepository.create(Store(16L,"Cra 15","Tienda mascotas",city))
        val store= entityManager.find(Store::class.java,16L)
        Assertions.assertNotNull(store)
        Assertions.assertEquals("Cra 15",store.address)
        Assertions.assertEquals("Tienda mascotas",store.name)
        Assertions.assertEquals(15L,store.city?.id)
        Assertions.assertEquals("Armenia",store.city?.name)

    }
    @Test
    fun findTest(){
        val city= City(15L,"Armenia")
        entityManager.persist(city)
        entityManager.persist(Store(16L,"Cra 15","Tienda mascotas",city))

        val store= storeRepository.find(16L)

        Assertions.assertNotNull(store)
        Assertions.assertEquals("Cra 15",store?.address)
        Assertions.assertEquals("Tienda mascotas",store?.name)
        Assertions.assertEquals(15L,store?.city?.id)
        Assertions.assertEquals("Armenia",store?.city?.name)
    }

    @Test
    fun updateTest(){
        val city= City(15L,"Armenia")
        entityManager.persist(city)
        entityManager.persist(Store(16L,"Cra 15","Tienda mascotas",city))
        entityManager.flush()

        val store= entityManager.find(Store::class.java,16L)

        store.name= "Tienda de videojuegos"
        store.address="Cra 17"

        entityManager.clear()
        storeRepository.update(store)
        val storeAssert= entityManager.find(Store::class.java,16L)
        Assertions.assertEquals("Tienda de videojuegos",storeAssert.name)
        Assertions.assertEquals("Cra 17",storeAssert.address)


    }

    @Test
    fun deleteTest(){
        val city= City(15L,"Armenia")
        entityManager.persist(city)
        entityManager.persist(Store(16L,"Cra 15","Tienda mascotas",city))

        storeRepository.delete(16L)

        val store= entityManager.find(Store::class.java,16L)

        Assertions.assertNull(store)

    }
    @Test
    fun listStoreTest(){
        val city= City(15L,"Armenia")
        entityManager.persist(city)
        val storeOne= Store(16L,"Cra 15","Tienda mascotas",city)
        val storeTwo= Store(17L,"Cra 16","Tienda helados",city)
        val storeThree= Store(18L,"Cra 18","Tienda videojuegos",city)
        entityManager.persist(storeOne)
        entityManager.persist(storeTwo)
        entityManager.persist(storeThree)

        val list= storeRepository.listStore()

        Assertions.assertEquals(3,list.size)
        Assertions.assertEquals("Tienda mascotas",list[0].name)
        Assertions.assertEquals("Cra 15",list[0].address)
        Assertions.assertEquals("Tienda helados",list[1].name)
        Assertions.assertEquals("Cra 16",list[1].address)
        Assertions.assertEquals("Tienda videojuegos",list[2].name)
        Assertions.assertEquals("Cra 18",list[2].address)
    }
}