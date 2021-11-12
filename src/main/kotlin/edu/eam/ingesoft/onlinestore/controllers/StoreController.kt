package edu.eam.ingesoft.onlinestore.controllers

import edu.eam.ingesoft.onlinestore.model.entities.Product
import edu.eam.ingesoft.onlinestore.model.entities.Store
import edu.eam.ingesoft.onlinestore.model.request.ProductRequest
import edu.eam.ingesoft.onlinestore.services.ProductStoreService
import edu.eam.ingesoft.onlinestore.services.StoreService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*

@RestController

@RequestMapping("/stores")
class StoreController {

    @Autowired
    lateinit var productStoreService: ProductStoreService

    @Autowired
    lateinit var storeService: StoreService

    @PostMapping("/product")
    fun createProductInStore(@RequestBody productRequest: ProductRequest){
        productStoreService.createProductInStore(productRequest)
    }

    @PostMapping("/cities/{id}")
    fun createStore(@PathVariable("id") idCity:Long,@RequestBody store: Store){
       storeService.createStore(store,idCity)
    }

    @PutMapping("{id}")
    fun editStore(@PathVariable("id") idStore:Long,@RequestBody store: Store){
        store.id= idStore
        storeService.editStore(store,idStore)
    }

    @GetMapping()
    fun findAllStores():List<Store>{
        val stores= storeService.findAllStores()
        return stores
    }

    @GetMapping("{id}/products")
    fun findProductByStore(@PathVariable("id") idStore: Long):List<Product>{
        val listProductByStore=storeService.findProductsByStore(idStore)
        return listProductByStore
    }

    @GetMapping("{id}/categories/{idCategory}/products")
    fun findByStoreAndByCategory(@PathVariable("id")idStore: Long,
                                 @PathVariable("idCategory") idCategory:Long):List<Product>{
        val listProductByStoreAndByCategory= storeService.findByStoreAndByCategory(idStore,idCategory)
        return listProductByStoreAndByCategory
    }
}