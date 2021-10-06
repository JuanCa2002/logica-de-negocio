package edu.eam.ingesoft.onlinestore.repositories

import edu.eam.ingesoft.onlinestore.model.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.transaction.annotation.Transactional
import javax.persistence.EntityManager

@SpringBootTest
@Transactional
class ProductStoreRepositoryTest {

    @Autowired
    lateinit var entityManager: EntityManager

    @Autowired
    lateinit var productStoreRepository: ProductStoreRepository

    @Test
    fun createTest(){
        val category = Category(15L,"alimento")
        val product= Product("1","Detergente","Ariel",category)
        val city= City(15L,"Armenia")
        val store= Store(16L,"Cra 15","Tienda mascotas",city)
        entityManager.persist(category)
        entityManager.persist(product)
        entityManager.persist(city)
        entityManager.persist(store)
        productStoreRepository.create(ProductStore(4L,50000.0,15.0,product,store))
        val productStore= entityManager.find(ProductStore::class.java,4L)
        Assertions.assertNotNull(productStore)
        Assertions.assertEquals(50000.0,productStore.price)
        Assertions.assertEquals(15.0,productStore.stock)
        Assertions.assertEquals("Detergente",productStore.product.name)
        Assertions.assertEquals("Ariel",productStore.product.branch)
        Assertions.assertEquals("alimento",productStore.product.category.name)
        Assertions.assertEquals("Cra 15",productStore.store.address)
        Assertions.assertEquals("Tienda mascotas",productStore.store.name)
        Assertions.assertEquals("Armenia",productStore.store.city.name)

    }
    @Test
    fun updateTest(){
        val category = Category(15L,"alimento")
        val product= Product("1","Detergente","Ariel",category)
        val city= City(15L,"Armenia")
        val store= Store(16L,"Cra 15","Tienda mascotas",city)
        entityManager.persist(category)
        entityManager.persist(product)
        entityManager.persist(city)
        entityManager.persist(store)
        entityManager.persist(ProductStore(4L,50000.0,15.0,product,store))
        entityManager.flush()

        val productStore= entityManager.find(ProductStore::class.java,4L)
        productStore.stock=16.0
        productStore.price=60000.0

        entityManager.clear()
        productStoreRepository.update(productStore)

        val productStoreAssert= entityManager.find(ProductStore::class.java,4L)
        Assertions.assertNotNull(productStoreAssert)
        Assertions.assertEquals(60000.0,productStoreAssert.price)
        Assertions.assertEquals(16.0,productStoreAssert.stock)

    }

    @Test
    fun findTest(){
        val category = Category(15L,"alimento")
        val product= Product("1","Detergente","Ariel",category)
        val city= City(15L,"Armenia")
        val store= Store(16L,"Cra 15","Tienda mascotas",city)
        entityManager.persist(category)
        entityManager.persist(product)
        entityManager.persist(city)
        entityManager.persist(store)
        entityManager.persist(ProductStore(4L,50000.0,15.0,product,store))
        val productStore= productStoreRepository.find(4L)
        Assertions.assertNotNull(productStore)
        Assertions.assertEquals(50000.0,productStore?.price)
        Assertions.assertEquals(15.0,productStore?.stock)
        Assertions.assertEquals("Detergente",productStore?.product?.name)
        Assertions.assertEquals("Ariel",productStore?.product?.branch)
        Assertions.assertEquals("alimento",productStore?.product?.category?.name)
        Assertions.assertEquals("Cra 15",productStore?.store?.address)
        Assertions.assertEquals("Tienda mascotas",productStore?.store?.name)
        Assertions.assertEquals("Armenia",productStore?.store?.city?.name)


    }

    @Test
    fun deleteTest(){
        val category = Category(15L,"alimento")
        val product= Product("1","Detergente","Ariel",category)
        val city= City(15L,"Armenia")
        val store= Store(16L,"Cra 15","Tienda mascotas",city)
        entityManager.persist(category)
        entityManager.persist(product)
        entityManager.persist(city)
        entityManager.persist(store)
        entityManager.persist(ProductStore(4L,50000.0,15.0,product,store))

        productStoreRepository.delete(4L)

        val productStore= entityManager.find(ProductStore::class.java,4L)

        Assertions.assertNull(productStore)

    }
    @Test
    fun findByStoreTest(){
        val category = Category(15L,"alimento")
        val productOne= Product("1","Detergente","Ariel",category)
        val productTwo= Product("2","cuido","Ringo",category)
        val productThree= Product("3","papitas","Margarita",category)
        val city= City(15L,"Armenia")
        val storeOne= Store(16L,"Cra 15","Tienda mascotas",city)
        val storeTwo= Store(17L,"Cra 17","Tienda helados",city)
        entityManager.persist(category)
        entityManager.persist(productOne)
        entityManager.persist(productTwo)
        entityManager.persist(productThree)
        entityManager.persist(city)
        entityManager.persist(storeOne)
        entityManager.persist(storeTwo)
        entityManager.persist(ProductStore(4L,50000.0,15.0,productOne,storeOne))
        entityManager.persist(ProductStore(5L,50000.0,15.0,productTwo,storeOne))
        entityManager.persist(ProductStore(6L,50000.0,15.0,productThree,storeTwo))

        val listOne=productStoreRepository.findByStore(16L)
        val listTwo= productStoreRepository.findByStore(17L)

        Assertions.assertEquals(2,listOne.size)
        Assertions.assertEquals(1,listTwo.size)


    }

    @Test
    fun findByStoreAndByCategoryTest(){
        val categoryOne = Category(15L,"alimento")
        val categoryTwo= Category(20L,"aseo")
        val productOne= Product("1","Detergente","Ariel",categoryOne)
        val productTwo= Product("2","cuido","Ringo",categoryOne)
        val productThree= Product("3","papitas","Margarita",categoryTwo)
        val city= City(15L,"Armenia")
        val storeOne= Store(16L,"Cra 15","Tienda mascotas",city)
        val storeTwo= Store(17L,"Cra 17","Tienda helados",city)
        entityManager.persist(categoryOne)
        entityManager.persist(categoryTwo)
        entityManager.persist(productOne)
        entityManager.persist(productTwo)
        entityManager.persist(productThree)
        entityManager.persist(city)
        entityManager.persist(storeOne)
        entityManager.persist(storeTwo)
        entityManager.persist(ProductStore(4L,50000.0,15.0,productOne,storeOne))
        entityManager.persist(ProductStore(5L,50000.0,15.0,productTwo,storeOne))
        entityManager.persist(ProductStore(6L,50000.0,15.0,productThree,storeTwo))

        val listOne=productStoreRepository.findByStoreAndByCategory(16L,15L)
        val listTwo= productStoreRepository.findByStoreAndByCategory(17L,20L)

        Assertions.assertEquals(2,listOne.size)
        Assertions.assertEquals(1,listTwo.size)


    }
}