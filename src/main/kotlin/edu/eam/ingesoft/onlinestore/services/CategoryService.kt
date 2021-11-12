package edu.eam.ingesoft.onlinestore.services

import edu.eam.ingesoft.onlinestore.exceptions.BusinessException
import edu.eam.ingesoft.onlinestore.model.entities.Category
import edu.eam.ingesoft.onlinestore.repositories.CategoryRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class CategoryService {

    @Autowired
    lateinit var categoryRepository: CategoryRepository

    fun createCategory(category: Category){
        val categoryById= categoryRepository.find(category.id)

        if (categoryById!=null){
            throw BusinessException("This category Already exist")
        }
        categoryRepository.create(category)
    }



}