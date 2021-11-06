package edu.eam.ingesoft.onlinestore.repositories

import edu.eam.ingesoft.onlinestore.model.entities.Product
import edu.eam.ingesoft.onlinestore.model.entities.ProductStore
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import javax.persistence.EntityManager

@Component
@Transactional


class ProductStoreRepository {
    @Autowired
    lateinit var em: EntityManager

    fun findByStore(id:Long?):List<Product>{
        val query= em.createQuery("SELECT ProSto.product FROM ProductStore ProSto WHERE ProSto.store.id =:idStore")
        query.setParameter("idStore",id)
        return query.resultList as List<Product>
    }


    fun findByStoreAndByCategory(id:Long,idCategory:Long):List<Product>{
        val query= em.createQuery("SELECT ProSto.product FROM ProductStore ProSto WHERE ProSto.store.id =:idStore and ProSto.product.category.id =:idCategory")
        query.setParameter("idStore",id)
        query.setParameter("idCategory",idCategory)
        return query.resultList as List<Product>
    }


    fun create(productStore: ProductStore){
        em.persist(productStore)
    }

    fun find(id:Long): ProductStore?{
        return em.find(ProductStore::class.java,id)
    }

    fun update(productStore: ProductStore) {
        em.merge(productStore)
    }

    fun delete(id: Long) {
        val productStore= find(id)
        if (productStore!=null) {
            em.remove(productStore)
        }
    }
}