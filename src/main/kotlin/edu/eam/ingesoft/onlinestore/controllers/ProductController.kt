package edu.eam.ingesoft.onlinestore.controllers

import edu.eam.ingesoft.onlinestore.model.entities.Product
import edu.eam.ingesoft.onlinestore.services.ProductService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*


@RestController

@RequestMapping("/products")
class ProductController {

    @Autowired
    lateinit var productService: ProductService

    @PostMapping("/category/{id}")
    fun createProduct(@PathVariable("id")idCategory:Long,@RequestBody product: Product){
        productService.createProduct(product,idCategory)
    }

    @PutMapping("/{id}")
    fun editProduct(@PathVariable("id")idProduct:String,@RequestBody product: Product){
         product.id= idProduct
         productService.editProduct(product,idProduct)
    }
}