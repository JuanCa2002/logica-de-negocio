package edu.eam.ingesoft.onlinestore.services

import edu.eam.ingesoft.onlinestore.exceptions.BusinessException
import edu.eam.ingesoft.onlinestore.repositories.StoreRepository
import edu.eam.ingesoft.onlinestore.model.Store
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class StoreService {

    @Autowired
    lateinit var storeRepository: StoreRepository

    fun createStore(store: Store){
        val storeById= storeRepository.find(store.id)

        if (storeById!=null){
            throw BusinessException("this store already exist")
        }

        storeRepository.create(store)
    }

    fun editStore(store: Store) {
        val storeById = storeRepository.find(store.id)

        if (storeById == null) {
            throw BusinessException("This store does not exist")
        }

        storeRepository.update(store)
    }
}