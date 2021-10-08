package edu.eam.ingesoft.onlinestore.services

import edu.eam.ingesoft.onlinestore.exceptions.BusinessException
import edu.eam.ingesoft.onlinestore.model.Category
import edu.eam.ingesoft.onlinestore.model.Product
import edu.eam.ingesoft.onlinestore.services.ProductService
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.transaction.annotation.Transactional
import javax.persistence.EntityManager


@SpringBootTest
@Transactional
class ProductServiceTest {

    @Autowired
    lateinit var productService: ProductService

    @Autowired
    lateinit var entityManager: EntityManager

    @Test
    fun createProductProductAlreadyExistsTest() {
        //prerequisitos
        val category = Category(15L,"alimento")
        entityManager.persist(category)
        entityManager.persist(Product("1","Detergente","Ariel",category))

        try {
            productService.createProduct(Product("1", "papitas", "Margarita", category))
            Assertions.fail()
        } catch (e: BusinessException) {
            Assertions.assertEquals("This product already exists", e.message)
        }
    }

    @Test
    fun createProductRepeatedNameTest() {
        //prerequisitos
        val category = Category(15L,"alimento")
        entityManager.persist(category)
        entityManager.persist(Product("1","Detergente","Ariel",category))

        val exception = Assertions.assertThrows(
            BusinessException::class.java,
            { productService.createProduct(Product("2", "Detergente", "Ariel", category)) }
        )

        Assertions.assertEquals("This product with this name already exists", exception.message)
    }


    @Test
    fun createProductHappyPathTest() {
        val category = Category(15L,"alimento")
        entityManager.persist(category)
        productService.createProduct(Product("1","Detergente","Ariel",category))

        //verificaciones..
        val productToAssert = entityManager.find(Product::class.java, "1")
        Assertions.assertNotNull(productToAssert)

        Assertions.assertEquals("Detergente", productToAssert.name)
    }

    @Test
    fun editProductNotExistTest(){
        val category = Category(15L,"alimento")
        entityManager.persist(category)
        val productUpdate= Product("1","Detergente","Ariel",category)

        try{
            productService.editProduct(productUpdate)
            Assertions.fail()
        } catch (e:BusinessException){
            Assertions.assertEquals("This product does not exist", e.message)
        }
    }

    @Test
    fun editProductRepeatedNameTest(){
        val category = Category(15L,"alimento")
        entityManager.persist(category)
        entityManager.persist(Product("1","Detergente","Ariel",category))
        val productUpdate=Product("1","Detergente","Ariel",category)
        try{
            productService.editProduct(productUpdate)
            Assertions.fail()
        } catch (e:BusinessException){
            Assertions.assertEquals("This product with this name already exists", e.message)
        }
    }

    @Test
    fun editProductHappyPathTest() {
        val category = Category(15L,"alimento")
        entityManager.persist(category)
        entityManager.persist(Product("1","Detergente","Ariel",category))
        val productUpdate= Product("1","Papitas","Ariel",category)
        productService.editProduct(productUpdate)

        //verificaciones..
        val productToAssert = entityManager.find(Product::class.java, "1")
        Assertions.assertNotNull(productToAssert)

        Assertions.assertEquals("Papitas", productToAssert.name)
    }
}