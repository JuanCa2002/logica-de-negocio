package edu.eam.ingesoft.onlinestore.services

import edu.eam.ingesoft.onlinestore.exceptions.BusinessException
import edu.eam.ingesoft.onlinestore.model.entities.Product
import edu.eam.ingesoft.onlinestore.repositories.StoreRepository
import edu.eam.ingesoft.onlinestore.model.entities.Store
import edu.eam.ingesoft.onlinestore.repositories.CategoryRepository
import edu.eam.ingesoft.onlinestore.repositories.CityRepository
import edu.eam.ingesoft.onlinestore.repositories.ProductStoreRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class StoreService {

    @Autowired
    lateinit var storeRepository: StoreRepository

    @Autowired
    lateinit var cityRepository: CityRepository

    @Autowired
    lateinit var productStoreRepository: ProductStoreRepository

    @Autowired
    lateinit var categoryRepository: CategoryRepository

    fun createStore(store: Store,idCity:Long){
        val city= cityRepository.find(idCity)

        if (city== null){
            throw BusinessException("This city does not exist")
        }
        val storeById= storeRepository.find(store.id)

        if (storeById!=null){
            throw BusinessException("this store already exist")
        }
        store.city= city
        storeRepository.create(store)
    }

    fun editStore(store: Store,idStore:Long) {
        val storeById = storeRepository.find(idStore)

        if (storeById == null) {
            throw BusinessException("This store does not exist")
        }
        store.city= storeById.city
        storeRepository.update(store)
    }

    fun findProductsByStore(idStore: Long):List<Product>{

        val store= storeRepository.find(idStore)

        if (store==null){
            throw BusinessException("This store does not exist")
        }

        val listProductByStore=productStoreRepository.findByStore(idStore)
        return listProductByStore

    }

    fun findByStoreAndByCategory(idStore: Long,idCategory: Long):List<Product>{

        val store= storeRepository.find(idStore)

        if (store==null){
            throw BusinessException("This store does not exist")
        }
        val category= categoryRepository.find(idCategory)

        if(category==null){
            throw BusinessException("This category does not exist")
        }

        val listProductByStoreAndByCategory=productStoreRepository.findByStoreAndByCategory(idStore,idCategory)
        return listProductByStoreAndByCategory

    }
    fun findAllStores():List<Store>{
        val stores= storeRepository.listStore()
        return stores
    }
}