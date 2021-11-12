package edu.eam.ingesoft.onlinestore.services

import edu.eam.ingesoft.onlinestore.exceptions.BusinessException
import edu.eam.ingesoft.onlinestore.model.entities.*
import edu.eam.ingesoft.onlinestore.model.request.ProductRequest
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.transaction.annotation.Transactional
import javax.persistence.EntityManager


@SpringBootTest
@Transactional
class ProductStoreServiceTest {


    @Autowired
    lateinit var productStoreService: ProductStoreService

    @Autowired
    lateinit var entityManager: EntityManager


    @Test
    fun createProductInStoreNotExistTest(){
        val category = Category(15L,"alimento")
        val productOne= Product("1","Detergente","Ariel",category)
        val productTwo= Product("2","cuido","Ringo",category)
        val productThree= Product("3","papitas","Margarita",category)
        val city= City(15L,"Armenia")
        val storeOne= Store(16L,"Cra 15","Tienda mascotas",city)

        entityManager.persist(category)
        entityManager.persist(productOne)
        entityManager.persist(productTwo)
        entityManager.persist(productThree)
        entityManager.persist(city)
        entityManager.persist(storeOne)
        entityManager.persist(ProductStore(4L,50000.0,15.0,productOne,storeOne))
        val newProduct= ProductRequest(5L,50000.0,15.0,productOne.id,storeOne.id)

        try{
            productStoreService.createProductInStore(newProduct)
            Assertions.fail()
        } catch (e: BusinessException){
            Assertions.assertEquals("This product already exists in this store", e.message)
        }

    }


    @Test
    fun createProductInStoreHappyPathTest(){
        val category = Category(15L,"alimento")
        val productOne= Product("1","Detergente","Ariel",category)
        val productTwo= Product("2","cuido","Ringo",category)
        val productThree= Product("3","papitas","Margarita",category)
        val city= City(15L,"Armenia")
        val storeOne= Store(16L,"Cra 15","Tienda mascotas",city)

        entityManager.persist(category)
        entityManager.persist(productOne)
        entityManager.persist(productTwo)
        entityManager.persist(productThree)
        entityManager.persist(city)
        entityManager.persist(storeOne)
        entityManager.persist(ProductStore(4L,50000.0,15.0,productOne,storeOne))
        val newProduct= ProductRequest(5L,50000.0,15.0,productTwo.id,storeOne.id)
        productStoreService.createProductInStore(newProduct)

        val productStore= entityManager.find(ProductStore::class.java,5L)

        Assertions.assertEquals(50000.0,productStore.price)
        Assertions.assertEquals(15.0,productStore.stock)
        Assertions.assertEquals("cuido",productStore.product.name)

    }

    @Test
    fun decreaseStockProductStoreNotExistTest(){

        try{
            productStoreService.decreaseStock(4L,14.0)
            Assertions.fail()
        }catch (e: BusinessException){
            Assertions.assertEquals("This product store does not exist", e.message)
        }


    }

    @Test
    fun decreaseStockLessThanZeroTest(){
        val category = Category(15L,"alimento")
        val productOne= Product("1","Detergente","Ariel",category)
        val city= City(15L,"Armenia")
        val store= Store(16L,"Cra 15","Tienda mascotas",city)
        entityManager.persist(category)
        entityManager.persist(productOne)
        entityManager.persist(city)
        entityManager.persist(store)
        entityManager.persist(ProductStore(4L,50000.0,15.0,productOne,store))

        try{
            productStoreService.decreaseStock(4L,16.0)
            Assertions.fail()
        }catch (e: BusinessException){
            Assertions.assertEquals("there can be no less than zero stocks", e.message)
        }

    }

    @Test
    fun decreaseStockProductStoreHappyPathTest(){
        val category = Category(15L,"alimento")
        val productOne= Product("1","Detergente","Ariel",category)
        val city= City(15L,"Armenia")
        val store= Store(16L,"Cra 15","Tienda mascotas",city)
        entityManager.persist(category)
        entityManager.persist(productOne)
        entityManager.persist(city)
        entityManager.persist(store)
        entityManager.persist(ProductStore(4L,50000.0,15.0,productOne,store))
        productStoreService.decreaseStock(4L,14.0)

        val productStore= entityManager.find(ProductStore::class.java,4L)
        Assertions.assertEquals(1.0,productStore.stock)
    }

    @Test
    fun increaseStockProductStoreNotExistTest(){

        try{
            productStoreService.increaseStock(4L,14.0)
            Assertions.fail()
        }catch (e: BusinessException){
            Assertions.assertEquals("This product store does not exist", e.message)
        }


    }

}