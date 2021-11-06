package edu.eam.ingesoft.onlinestore.controllers

import com.fasterxml.jackson.databind.ObjectMapper
import edu.eam.ingesoft.onlinestore.model.entities.Category
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

class CategoryControllerTest {

    @Autowired
    lateinit var mocMvc: MockMvc

    @Autowired
    lateinit var objectMapper: ObjectMapper

    @Autowired
    lateinit var entityManager: EntityManager

    @Test
    fun createCategoryAlreadyExist(){
        entityManager.persist(Category(15,"Aseo"))
        val body = """
           {
            "id":15,
            "name": "Comida"
            }
        """.trimIndent()
        val request = MockMvcRequestBuilders
            .post("/categories")
            .contentType(MediaType.APPLICATION_JSON)
            .content(body)
        val response = mocMvc.perform(request)
        val resp = response.andReturn().response
        Assertions.assertEquals(412, resp.status)
        Assertions.assertEquals("{\"message\":\"This category Already exist\",\"code\":412}".trimIndent(),
            resp.contentAsString)
    }

    @Test
    fun createCategoryHappyPath() {
        val body = """
           {
            "id":15,
            "name": "Comida"
            }
        """.trimIndent()
        val request = MockMvcRequestBuilders
            .post("/categories")
            .contentType(MediaType.APPLICATION_JSON)
            .content(body)
        val response = mocMvc.perform(request)
        val resp = response.andReturn().response
        Assertions.assertEquals(200, resp.status)
    }


}