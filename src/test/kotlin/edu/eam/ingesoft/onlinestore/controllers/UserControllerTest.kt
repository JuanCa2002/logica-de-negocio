package edu.eam.ingesoft.onlinestore.controllers

import com.fasterxml.jackson.databind.ObjectMapper
import edu.eam.ingesoft.onlinestore.model.entities.City
import edu.eam.ingesoft.onlinestore.model.entities.User
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


class UserControllerTest {

    @Autowired
    lateinit var mocMvc: MockMvc

    @Autowired
    lateinit var objectMapper: ObjectMapper

    @Autowired
    lateinit var entityManager: EntityManager

    @Test
    fun createUserCityDoesNotExist(){
        val body = """
           {
            "id":"555",
            "address":"Cra 18 #3-54",
            "name": "Juan Camilo",
            "lastName": "Torres"
            }
        """.trimIndent()
        val request = MockMvcRequestBuilders
            .post("/users/cities/18")
            .contentType(MediaType.APPLICATION_JSON)
            .content(body)
        val response = mocMvc.perform(request)
        val resp = response.andReturn().response
        Assertions.assertEquals(412, resp.status)
        Assertions.assertEquals("{\"message\":\"This city does not exist\",\"code\":412}".trimIndent(),
            resp.contentAsString)
    }

    @Test
    fun createUserAlreadyExist(){
        val city= City(15,"Armenia")
        entityManager.persist(city)
        entityManager.persist(User("1","Cra 22","Juan","Torres",city))
        val body = """
           {
            "id":"1",
            "address":"Cra 18 #3-54",
            "name": "Juan Camilo",
            "lastName": "Torres"
            }
        """.trimIndent()
        val request = MockMvcRequestBuilders
            .post("/users/cities/15")
            .contentType(MediaType.APPLICATION_JSON)
            .content(body)
        val response = mocMvc.perform(request)
        val resp = response.andReturn().response
        Assertions.assertEquals(412, resp.status)
        Assertions.assertEquals("{\"message\":\"this user already exist\",\"code\":412}".trimIndent(),
            resp.contentAsString)
    }

    @Test
    fun createUserHappyPath(){
        val city= City(15,"Armenia")
        entityManager.persist(city)
        val body = """
           {
            "id":"555",
            "address":"Cra 18 #3-54",
            "name": "Juan Camilo",
            "lastName": "Torres"
            }
        """.trimIndent()
        val request = MockMvcRequestBuilders
            .post("/users/cities/15")
            .contentType(MediaType.APPLICATION_JSON)
            .content(body)
        val response = mocMvc.perform(request)
        val resp = response.andReturn().response
        Assertions.assertEquals(200, resp.status)

    }

    @Test
    fun editUserDoesNotExist(){
        val body = """
           {
            "address":"Cra 18 #3-54",
            "name": "Juan Camilo",
            "lastName": "Torres"
            }
        """.trimIndent()
        val request = MockMvcRequestBuilders
            .put("/users/555")
            .contentType(MediaType.APPLICATION_JSON)
            .content(body)
        val response = mocMvc.perform(request)
        val resp = response.andReturn().response
        Assertions.assertEquals(412, resp.status)
        Assertions.assertEquals("{\"message\":\"This user does not exist\",\"code\":412}".trimIndent(),
            resp.contentAsString)

    }
    @Test
    fun editUserHappyPath(){
        val city= City(15,"Armenia")
        entityManager.persist(city)
        entityManager.persist(User("1","Cra 22","Juan","Torres",city))
        val body = """
           {
            "address":"Cra 20 #3-54",
            "name": "Jhoan",
            "lastName": "Torres"
            }
        """.trimIndent()
        val request = MockMvcRequestBuilders
            .put("/users/1")
            .contentType(MediaType.APPLICATION_JSON)
            .content(body)
        val response = mocMvc.perform(request)
        val resp = response.andReturn().response
        val user= entityManager.find(User::class.java,"1")
        Assertions.assertEquals(200, resp.status)
        Assertions.assertEquals("Cra 20 #3-54",user.address)
        Assertions.assertEquals("Jhoan",user.name)

    }

}