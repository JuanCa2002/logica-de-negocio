package edu.eam.ingesoft.onlinestore.controllers

import edu.eam.ingesoft.onlinestore.model.entities.Category
import edu.eam.ingesoft.onlinestore.services.CategoryService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController

@RequestMapping("/categories")
class CategoryController {

    @Autowired
    lateinit var categoryService: CategoryService

    @PostMapping
    fun create(@RequestBody category: Category){
        categoryService.createCategory(category)
    }
}