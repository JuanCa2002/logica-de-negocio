package edu.eam.ingesoft.onlinestore.repositories

import edu.eam.ingesoft.onlinestore.model.entities.Store
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import javax.persistence.EntityManager

@Component
@Transactional


class StoreRepository {

    fun listStore():List<Store>{
        val query= em.createQuery("SELECT store FROM Store store")
        return query.resultList as List<Store>
    }

    @Autowired
    lateinit var em: EntityManager


    fun create(store: Store){
        em.persist(store)
    }

    fun find(id:Long?): Store?{
        return em.find(Store::class.java,id)
    }

    fun update(store: Store) {
        em.merge(store)
    }

    fun delete(id: Long) {
        val store= find(id)
        if (store!=null) {
            em.remove(store
            )
        }
    }
}