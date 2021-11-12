package edu.eam.ingesoft.onlinestore.repositories


import edu.eam.ingesoft.onlinestore.model.entities.City
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import javax.persistence.EntityManager

@Component
@Transactional

class CityRepository {
    @Autowired
    lateinit var em: EntityManager


    fun create(city: City){
        em.persist(city)
    }

    fun find(id:Long): City?{
        return em.find(City::class.java,id)
    }

    fun update(city: City) {
        em.merge(city)
    }

    fun delete(id: Long) {
        val city= find(id)
        if (city!=null) {
            em.remove(city)
        }
    }
}