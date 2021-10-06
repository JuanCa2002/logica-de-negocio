package edu.eam.ingesoft.onlinestore.repositories

import edu.eam.ingesoft.onlinestore.model.Category
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.transaction.annotation.Transactional
import javax.persistence.EntityManager

@SpringBootTest
@Transactional
class CategoryRepositoryTest {

    @Autowired
    lateinit var entityManager: EntityManager

    @Autowired
    lateinit var categoryRepository: CategoryRepository

    @Test
    fun createTest(){
       categoryRepository.create(Category(15L,"alimento"))
       val category= entityManager.find(Category::class.java,15L)
       Assertions.assertNotNull(category)
       Assertions.assertEquals("alimento",category.name)

    }
    @Test
    fun findTest(){
        entityManager.persist(Category(15L,"alimento"))

        val category= categoryRepository.find(15L)

        Assertions.assertNotNull(category)
        Assertions.assertEquals("alimento",category?.name)
    }

    @Test
    fun updateTest(){
        entityManager.persist(Category(15L,"alimento"))
        entityManager.flush()

        val category= entityManager.find(Category::class.java,15L)
        category.name= "aseo"

        entityManager.clear()
        categoryRepository.update(category)
        val categoryAssert= entityManager.find(Category::class.java,15L)
        Assertions.assertEquals("aseo",categoryAssert.name)


    }

    @Test
    fun deleteTest(){
        entityManager.persist(Category(15L,"alimento"))

        categoryRepository.delete(15L)

        val category= entityManager.find(Category::class.java,15L)

        Assertions.assertNull(category)

    }


}