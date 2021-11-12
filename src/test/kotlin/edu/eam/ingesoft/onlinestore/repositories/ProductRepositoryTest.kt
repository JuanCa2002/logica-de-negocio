package edu.eam.ingesoft.onlinestore.repositories

import edu.eam.ingesoft.onlinestore.model.entities.Product
import edu.eam.ingesoft.onlinestore.model.entities.Category
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.transaction.annotation.Transactional
import javax.persistence.EntityManager

@SpringBootTest
@Transactional
class ProductRepositoryTest {
    @Autowired
    lateinit var entityManager: EntityManager

    @Autowired
    lateinit var productRepository: ProductRepository

    @Test
    fun createTest(){
        val category = Category(15L,"alimento")
        entityManager.persist(category)
        productRepository.create(Product("1","Detergente","Ariel",category))
        val product= entityManager.find(Product::class.java,"1")
        Assertions.assertNotNull(product)
        Assertions.assertEquals("Detergente",product.name)
        Assertions.assertEquals("Ariel",product.branch)
        Assertions.assertEquals(15L,product.category?.id)
        Assertions.assertEquals("alimento",product.category?.name)

    }
    @Test
    fun findTest(){
        val category = Category(15L,"alimento")
        entityManager.persist(category)
        entityManager.persist(Product("1","Detergente","Ariel",category))

        val product= productRepository.find("1")

        Assertions.assertNotNull(product)
        Assertions.assertEquals("Detergente",product?.name)
        Assertions.assertEquals("Ariel",product?.branch)
        Assertions.assertEquals(15L,product?.category?.id)
        Assertions.assertEquals("alimento",product?.category?.name)
    }

    @Test
    fun updateTest(){
        val category = Category(15L,"alimento")
        entityManager.persist(category)
        entityManager.persist(Product("1","Detergente","Ariel",category))
        entityManager.flush()

        val product= entityManager.find(Product::class.java,"1")
        product.name= "Arroz"
        product.branch="Arroba"

        entityManager.clear()
        productRepository.update(product)
        val productAssert= entityManager.find(Product::class.java,"1")
        Assertions.assertEquals("Arroz",productAssert.name)
        Assertions.assertEquals("Arroba",productAssert.branch)


    }

    @Test
    fun deleteTest(){
        val category = Category(15L,"alimento")
        entityManager.persist(Product("1","Detergente","Ariel",category))

        productRepository.delete("1")

        val product= entityManager.find(Product::class.java,"1")

        Assertions.assertNull(product)

    }

    @Test
    fun findByName(){
        val category = Category(15L,"alimento")
        entityManager.persist(category)
        entityManager.persist(Product("1","Detergente","Ariel",category))

        val product= productRepository.findByName("Detergente")

        Assertions.assertNotNull(product)
        Assertions.assertEquals("Detergente",product?.name)
        Assertions.assertEquals("Ariel",product?.branch)
        Assertions.assertEquals(15L,product?.category?.id)
        Assertions.assertEquals("alimento",product?.category?.name)
    }


}