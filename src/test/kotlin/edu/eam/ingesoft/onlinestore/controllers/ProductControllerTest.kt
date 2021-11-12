package edu.eam.ingesoft.onlinestore.controllers

import com.fasterxml.jackson.databind.ObjectMapper
import edu.eam.ingesoft.onlinestore.model.entities.Category
import edu.eam.ingesoft.onlinestore.model.entities.Product
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.transaction.annotation.Transactional
import javax.persistence.EntityManager

@SpringBootTest
@Transactional

@AutoConfigureMockMvc

class ProductControllerTest {

    @Autowired
    lateinit var mocMvc: MockMvc

    @Autowired
    lateinit var objectMapper: ObjectMapper

    @Autowired
    lateinit var entityManager: EntityManager

    @Test
    fun createProductCategoryDoesNotExist(){
        val body = """
           {
            "id": "888",
            "name": "Galletas",
            "branch": "Oreo"
           }
        """.trimIndent()
        val request = MockMvcRequestBuilders
            .post("/products/category/3")
            .contentType(MediaType.APPLICATION_JSON)
            .content(body)
        val response = mocMvc.perform(request)
        val resp = response.andReturn().response
        Assertions.assertEquals(412, resp.status)
        Assertions.assertEquals("{\"message\":\"This category does not exist\",\"code\":412}".trimIndent(),
            resp.contentAsString)
    }
    @Test
    fun createProductAlreadyExist(){
        val category= Category(2,"Aseo")
        entityManager.persist(category)
        entityManager.persist(Product("888","Detergente","Ariel",category))
        val body = """
           {
            "id": "888",
            "name": "Galletas",
            "branch": "Oreo"
           }
        """.trimIndent()
        val request = MockMvcRequestBuilders
            .post("/products/category/2")
            .contentType(MediaType.APPLICATION_JSON)
            .content(body)
        val response = mocMvc.perform(request)
        val resp = response.andReturn().response
        Assertions.assertEquals(412, resp.status)
        Assertions.assertEquals("{\"message\":\"This product already exists\",\"code\":412}".trimIndent(),
            resp.contentAsString)
    }

    @Test
    fun createProductAlreadyExistWithThisName(){
        val category= Category(2,"Aseo")
        entityManager.persist(category)
        entityManager.persist(Product("55","Detergente","Ariel",category))
        val body = """
           {
            "id": "888",
            "name": "Detergente",
            "branch": "Oreo"
           }
        """.trimIndent()
        val request = MockMvcRequestBuilders
            .post("/products/category/2")
            .contentType(MediaType.APPLICATION_JSON)
            .content(body)
        val response = mocMvc.perform(request)
        val resp = response.andReturn().response
        Assertions.assertEquals(412, resp.status)
        Assertions.assertEquals("{\"message\":\"This product with this name already exists\",\"code\":412}".trimIndent(),
            resp.contentAsString)
    }

    @Test
    fun createProductHappyPath(){
        val category= Category(2,"Aseo")
        entityManager.persist(category)
        val body = """
           {
            "id": "888",
            "name": "Detergente",
            "branch": "Oreo"
           }
        """.trimIndent()
        val request = MockMvcRequestBuilders
            .post("/products/category/2")
            .contentType(MediaType.APPLICATION_JSON)
            .content(body)
        val response = mocMvc.perform(request)
        val resp = response.andReturn().response
        Assertions.assertEquals(200, resp.status)


    }

    @Test
    fun editProductDoesNotExist(){
        val body = """
           {
            "name": "Detergente",
            "branch": "Oreo"
           }
        """.trimIndent()
        val request = MockMvcRequestBuilders
            .put("/products/111")
            .contentType(MediaType.APPLICATION_JSON)
            .content(body)
        val response = mocMvc.perform(request)
        val resp = response.andReturn().response
        Assertions.assertEquals(412, resp.status)
        Assertions.assertEquals("{\"message\":\"This product does not exist\",\"code\":412}".trimIndent(),
            resp.contentAsString)

    }
    @Test
    fun editProductWithThisNameAlreadyExist(){
        val category= Category(2,"Aseo")
        entityManager.persist(category)
        entityManager.persist(Product("55","Detergente","Ariel",category))
        val body = """
           {
            "name": "Detergente",
            "branch": "Oreo"
           }
        """.trimIndent()
        val request = MockMvcRequestBuilders
            .put("/products/55")
            .contentType(MediaType.APPLICATION_JSON)
            .content(body)
        val response = mocMvc.perform(request)
        val resp = response.andReturn().response
        Assertions.assertEquals(412, resp.status)
        Assertions.assertEquals("{\"message\":\"This product with this name already exists\",\"code\":412}".trimIndent(),
            resp.contentAsString)

    }

    @Test
    fun editProductHappyPath(){
        val category= Category(2,"Aseo")
        entityManager.persist(category)
        entityManager.persist(Product("55","Detergente","Ariel",category))
        val body = """
           {
            "name": "Acondicionador",
            "branch": "Oreo"
           }
        """.trimIndent()
        val request = MockMvcRequestBuilders
            .put("/products/55")
            .contentType(MediaType.APPLICATION_JSON)
            .content(body)
        val response = mocMvc.perform(request)
        val resp = response.andReturn().response
        val product= entityManager.find(Product::class.java,"55")
        Assertions.assertEquals(200, resp.status)
        Assertions.assertEquals("Acondicionador",product.name)
        Assertions.assertEquals("Oreo",product.branch)
    }

}