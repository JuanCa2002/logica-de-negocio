package edu.eam.ingesoft.onlinestore.controllers

import com.fasterxml.jackson.databind.ObjectMapper
import edu.eam.ingesoft.onlinestore.exceptions.ErrorResponse
import edu.eam.ingesoft.onlinestore.model.entities.*
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import org.springframework.transaction.annotation.Transactional
import javax.persistence.EntityManager

@SpringBootTest
@Transactional

@AutoConfigureMockMvc
class StoreControllerTest {
    @Autowired
    lateinit var mocMvc: MockMvc

    @Autowired
    lateinit var objectMapper: ObjectMapper

    @Autowired
    lateinit var entityManager: EntityManager

    @Test
    fun createProductInStoreProductDoesNotExist(){
        val body = """
           {
             "id":15,
            "price":1800.0,
            "stock":19.0,
            "idProduct": "888",
            "idStore": 222
           }
        """.trimIndent()
        val request = MockMvcRequestBuilders
            .post("/stores/product")
            .contentType(MediaType.APPLICATION_JSON)
            .content(body)
        val response = mocMvc.perform(request)
        val resp = response.andReturn().response
        Assertions.assertEquals(412, resp.status)
        Assertions.assertEquals("{\"message\":\"This product does not exits\",\"code\":412}".trimIndent(),
            resp.contentAsString)
    }

    @Test
    fun createProductInStoreThisStoreDoesNotExist(){
        val category= Category(2,"Aseo")
        entityManager.persist(category)
        entityManager.persist(Product("888","Detergente","Ariel",category))
        val body = """
           {
             "id":15,
            "price":1800.0,
            "stock":19.0,
            "idProduct": "888",
            "idStore": 222
           }
        """.trimIndent()
        val request = MockMvcRequestBuilders
            .post("/stores/product")
            .contentType(MediaType.APPLICATION_JSON)
            .content(body)
        val response = mocMvc.perform(request)
        val resp = response.andReturn().response
        Assertions.assertEquals(412, resp.status)
        Assertions.assertEquals("{\"message\":\"This store does not exist\",\"code\":412}".trimIndent(),
            resp.contentAsString)
    }

    @Test
    fun createProductInStoreThisProductAlreadyExistInStore(){
        val category= Category(2,"Aseo")
        entityManager.persist(category)
        val product=Product("888","Detergente","Ariel",category)
        entityManager.persist(product)
        val city=City(15L,"Armenia")
        entityManager.persist(city)
        val store= Store(16,"Cra 15","Tienda mascotas",city)
        entityManager.persist(store)
        entityManager.persist(ProductStore(15,1200.0,15.0,product,store))
        val body = """
           {
             "id":15,
            "price":1800.0,
            "stock":19.0,
            "idProduct": "888",
            "idStore": 16
           }
        """.trimIndent()
        val request = MockMvcRequestBuilders
            .post("/stores/product")
            .contentType(MediaType.APPLICATION_JSON)
            .content(body)
        val response = mocMvc.perform(request)
        val resp = response.andReturn().response
        Assertions.assertEquals(412, resp.status)
        Assertions.assertEquals("{\"message\":\"This product already exists in this store\",\"code\":412}".trimIndent(),
            resp.contentAsString)
    }

    @Test
    fun createProductInStoreHappyPath(){
        val category= Category(2,"Aseo")
        entityManager.persist(category)
        val product=Product("888","Detergente","Ariel",category)
        entityManager.persist(product)
        val city=City(15L,"Armenia")
        entityManager.persist(city)
        val store= Store(16,"Cra 15","Tienda mascotas",city)
        entityManager.persist(store)
        val body = """
           {
             "id":15,
            "price":1800.0,
            "stock":19.0,
            "idProduct": "888",
            "idStore": 16
           }
        """.trimIndent()
        val request = MockMvcRequestBuilders
            .post("/stores/product")
            .contentType(MediaType.APPLICATION_JSON)
            .content(body)
        val response = mocMvc.perform(request)
        val resp = response.andReturn().response
        Assertions.assertEquals(200, resp.status)
    }

    @Test
    fun createStoreCityDoesNotExist(){
        val body = """
           {
             "id":15,
            "address":"Cra 18 # 3 - 58",
            "name":"Supermercado el trillas"
            
           }
        """.trimIndent()
        val request = MockMvcRequestBuilders
            .post("/stores/cities/18")
            .contentType(MediaType.APPLICATION_JSON)
            .content(body)
        val response = mocMvc.perform(request)
        val resp = response.andReturn().response
        Assertions.assertEquals(412, resp.status)
        Assertions.assertEquals("{\"message\":\"This city does not exist\",\"code\":412}".trimIndent(),
            resp.contentAsString)
    }

    @Test
    fun createStoreAlreadyExist(){
        val city=City(15,"Armenia")
        entityManager.persist(city)
        val store= Store(16,"Cra 15","Tienda mascotas",city)
        entityManager.persist(store)
        val body = """
           {
             "id":16,
            "address":"Cra 18 # 3 - 58",
            "name":"Supermercado el trillas"
            
           }
        """.trimIndent()
        val request = MockMvcRequestBuilders
            .post("/stores/cities/15")
            .contentType(MediaType.APPLICATION_JSON)
            .content(body)
        val response = mocMvc.perform(request)
        val resp = response.andReturn().response
        Assertions.assertEquals(412, resp.status)
        Assertions.assertEquals("{\"message\":\"this store already exist\",\"code\":412}".trimIndent(),
            resp.contentAsString)
    }

    @Test
    fun createStoreHappyPath(){
        val city=City(15,"Armenia")
        entityManager.persist(city)
        val body = """
           {
             "id":16,
            "address":"Cra 18 # 3 - 58",
            "name":"Supermercado el trillas"
            
           }
        """.trimIndent()
        val request = MockMvcRequestBuilders
            .post("/stores/cities/15")
            .contentType(MediaType.APPLICATION_JSON)
            .content(body)
        val response = mocMvc.perform(request)
        val resp = response.andReturn().response
        Assertions.assertEquals(200, resp.status)

    }
    @Test
    fun editStoreDoesNotExist(){
        val body = """
           {
             
            "address":"Cra 18 # 3 - 58",
            "name":"Supermercado el trillas"
            
           }
        """.trimIndent()
        val request = MockMvcRequestBuilders
            .put("/stores/222")
            .contentType(MediaType.APPLICATION_JSON)
            .content(body)
        val response = mocMvc.perform(request)
        val resp = response.andReturn().response
        Assertions.assertEquals(412, resp.status)
        Assertions.assertEquals("{\"message\":\"This store does not exist\",\"code\":412}".trimIndent(),
            resp.contentAsString)
    }

    @Test
    fun editStoreHappyPath(){
        val city=City(15,"Armenia")
        entityManager.persist(city)
        val store= Store(16L,"Cra 15","Tienda mascotas",city)
        entityManager.persist(store)
        val body = """
           {
             "address":"Cra 18 # 3 - 58",
            "name":"Supermercado el trillas"
            
           }
        """.trimIndent()
        val request = MockMvcRequestBuilders
            .put("/stores/16")
            .contentType(MediaType.APPLICATION_JSON)
            .content(body)
        val response = mocMvc.perform(request)
        val resp = response.andReturn().response
        val storeUpdate= entityManager.find(Store::class.java,16L)
        Assertions.assertEquals(200, resp.status)
        Assertions.assertEquals("Cra 18 # 3 - 58",store.address)
        Assertions.assertEquals("Supermercado el trillas",store.name)
    }

    @Test
    fun findAllStoresHappyPath(){
        val city=City(15,"Armenia")
        entityManager.persist(city)
        val storeOne= Store(16L,"Cra 15","Tienda mascotas",city)
        entityManager.persist(storeOne)
        val storeTwo= Store(17L,"Cra 16","Supermercado el Trillal",city)
        entityManager.persist(storeTwo)

        val request = MockMvcRequestBuilders.get("/stores")
            .contentType(MediaType.APPLICATION_JSON)

        val response = mocMvc.perform(request)
        response.andExpect(MockMvcResultMatchers.status().isOk)

        val json = response.andReturn().response.contentAsString;

        val  stores = objectMapper.readValue(json, Array<Store>::class.java)
        Assertions.assertEquals(2,stores.size)
        Assertions.assertEquals("Cra 15",stores[0].address)
        Assertions.assertEquals("Tienda mascotas",stores[0].name)
        Assertions.assertEquals("Cra 16",stores[1].address)
        Assertions.assertEquals("Supermercado el Trillal",stores[1].name)
    }

    @Test
    fun findProductsByStoreThisStoreDoesNotExist(){
        val request = MockMvcRequestBuilders.get("/stores/222/products")
            .contentType(MediaType.APPLICATION_JSON)

        val response = mocMvc.perform(request)
        response.andExpect(MockMvcResultMatchers.status().isPreconditionFailed)

        val json = response.andReturn().response.contentAsString;
        val  error= objectMapper.readValue(json, ErrorResponse::class.java)
        Assertions.assertEquals("This store does not exist", error.message)
        Assertions.assertEquals(412, error.code)
    }

    @Test
    fun findProductsByStoreHappyPath(){
        val category= Category(2,"Aseo")
        entityManager.persist(category)
        val productOne=Product("888","Detergente","Ariel",category)
        entityManager.persist(productOne)
        val productTwo=Product("999","Croquetas","Ringo",category)
        entityManager.persist(productTwo)
        val productThree=Product("1010","Jugo de mango","Tutifruti",category)
        entityManager.persist(productThree)
        val city=City(15L,"Armenia")
        entityManager.persist(city)
        val storeOne= Store(16,"Cra 15","Tienda mascotas",city)
        entityManager.persist(storeOne)
        val storeTwo= Store(17,"Cra 16","Supermercado el trillal",city)
        entityManager.persist(storeTwo)
        entityManager.persist(ProductStore(15,1200.0,15.0,productTwo,storeOne))
        entityManager.persist(ProductStore(16,1800.0,18.0,productOne,storeTwo))
        entityManager.persist(ProductStore(17,1900.0,20.0,productThree,storeTwo))
        val request = MockMvcRequestBuilders.get("/stores/17/products")
            .contentType(MediaType.APPLICATION_JSON)

        val response = mocMvc.perform(request)
        response.andExpect(MockMvcResultMatchers.status().isOk)

        val json = response.andReturn().response.contentAsString;

        val  productsByStoreTwo= objectMapper.readValue(json, Array<Product>::class.java)
        Assertions.assertEquals(2,productsByStoreTwo.size)
        Assertions.assertEquals("Detergente",productsByStoreTwo[0].name)
        Assertions.assertEquals("Ariel",productsByStoreTwo[0].branch)
        Assertions.assertEquals("Jugo de mango",productsByStoreTwo[1].name)
        Assertions.assertEquals("Tutifruti",productsByStoreTwo[1].branch)

    }

    @Test
    fun findProductsByStoreAndByCategoryThisStoreDoesNotExist(){
        val request = MockMvcRequestBuilders.get("/stores/222/categories/1/products")
            .contentType(MediaType.APPLICATION_JSON)

        val response = mocMvc.perform(request)
        response.andExpect(MockMvcResultMatchers.status().isPreconditionFailed)

        val json = response.andReturn().response.contentAsString;
        val  error= objectMapper.readValue(json, ErrorResponse::class.java)
        Assertions.assertEquals("This store does not exist", error.message)
        Assertions.assertEquals(412, error.code)
    }
    @Test
    fun findProductsByStoreAndByCategoryThisCategoryDoesNotExist(){
        val city=City(15L,"Armenia")
        entityManager.persist(city)
        val store= Store(16,"Cra 15","Tienda mascotas",city)
        entityManager.persist(store)
        val request = MockMvcRequestBuilders.get("/stores/16/categories/1/products")
            .contentType(MediaType.APPLICATION_JSON)

        val response = mocMvc.perform(request)
        response.andExpect(MockMvcResultMatchers.status().isPreconditionFailed)

        val json = response.andReturn().response.contentAsString;
        val  error= objectMapper.readValue(json, ErrorResponse::class.java)
        Assertions.assertEquals("This category does not exist", error.message)
        Assertions.assertEquals(412, error.code)
    }

    @Test
    fun findProductsByStoreAndByCategoryHappyPath(){
        val categoryOne= Category(1,"Aseo")
        entityManager.persist(categoryOne)
        val categoryTwo= Category(2,"Consumo")
        entityManager.persist(categoryTwo)
        val productOne=Product("888","Detergente","Ariel",categoryOne)
        entityManager.persist(productOne)
        val productTwo=Product("999","Croquetas","Ringo",categoryTwo)
        entityManager.persist(productTwo)
        val productThree=Product("1010","Jugo de mango","Tutifruti",categoryTwo)
        entityManager.persist(productThree)
        val city=City(15L,"Armenia")
        entityManager.persist(city)
        val store= Store(16,"Cra 15","Tienda mascotas",city)
        entityManager.persist(store)
        entityManager.persist(ProductStore(15,1200.0,15.0,productTwo,store))
        entityManager.persist(ProductStore(16,1800.0,18.0,productOne,store))
        entityManager.persist(ProductStore(17,1900.0,20.0,productThree,store))
        val request = MockMvcRequestBuilders.get("/stores/16/categories/2/products")
            .contentType(MediaType.APPLICATION_JSON)

        val response = mocMvc.perform(request)
        response.andExpect(MockMvcResultMatchers.status().isOk)

        val json = response.andReturn().response.contentAsString;

        val  productsByStoreAndByCategory= objectMapper.readValue(json, Array<Product>::class.java)
        Assertions.assertEquals(2,productsByStoreAndByCategory.size)
        Assertions.assertEquals("Croquetas",productsByStoreAndByCategory[0].name)
        Assertions.assertEquals("Ringo",productsByStoreAndByCategory[0].branch)
        Assertions.assertEquals("Jugo de mango",productsByStoreAndByCategory[1].name)
        Assertions.assertEquals("Tutifruti",productsByStoreAndByCategory[1].branch)
    }
}