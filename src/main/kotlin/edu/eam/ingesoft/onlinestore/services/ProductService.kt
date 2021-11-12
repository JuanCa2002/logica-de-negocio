package edu.eam.ingesoft.onlinestore.services

import edu.eam.ingesoft.onlinestore.model.entities.Product
import edu.eam.ingesoft.onlinestore.exceptions.BusinessException
import edu.eam.ingesoft.onlinestore.repositories.CategoryRepository
import edu.eam.ingesoft.onlinestore.repositories.ProductRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class ProductService {
    @Autowired
    lateinit var productRepository: ProductRepository

    @Autowired
    lateinit var categoryRepository: CategoryRepository

    fun createProduct(product: Product, idCategory:Long){

        val categoryById=categoryRepository.find(idCategory)

        if ( categoryById == null){
            throw BusinessException("This category does not exist")
        }

        val productById= productRepository.find(product.id)

        if (productById!=null){
            throw BusinessException("This product already exists")
        }

        val productByName= productRepository.findByName(product.name)

        if(productByName!=null){
            throw BusinessException("This product with this name already exists")
        }
        product.category= categoryById
        productRepository.create(product)

    }

    fun editProduct(product: Product, idProduct: String){
        val productById = productRepository.find(idProduct)

        if (productById == null) {
            throw BusinessException("This product does not exist")
        }

        val productByName= productRepository.findByName(product.name)

        if(productByName!=null){
            throw BusinessException("This product with this name already exists")
        }
        product.category= productById.category
        productRepository.update(product)
    }
}