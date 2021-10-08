package edu.eam.ingesoft.onlinestore.services

import edu.eam.ingesoft.onlinestore.model.Product
import edu.eam.ingesoft.onlinestore.exceptions.BusinessException
import edu.eam.ingesoft.onlinestore.repositories.ProductRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class ProductService {
    @Autowired
    lateinit var productRepository: ProductRepository

    fun createProduct(product: Product){
        val productById= productRepository.find(product.id)

        if (productById!=null){
            throw BusinessException("This product already exists")
        }

        val productByName= productRepository.findByName(product.name)

        if(productByName!=null){
            throw BusinessException("This product with this name already exists")
        }

        productRepository.create(product)

    }

    fun editProduct(product: Product){
        val productById = productRepository.find(product.id)

        if (productById == null) {
            throw BusinessException("This product does not exist")
        }

        val productByName= productRepository.findByName(product.name)

        if(productByName!=null){
            throw BusinessException("This product with this name already exists")
        }

        productRepository.update(product)
    }
}