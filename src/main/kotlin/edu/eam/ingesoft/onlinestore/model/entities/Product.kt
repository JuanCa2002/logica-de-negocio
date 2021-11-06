package edu.eam.ingesoft.onlinestore.model.entities

import edu.eam.ingesoft.onlinestore.model.entities.Category
import java.io.Serializable
import javax.persistence.*

@Entity
@Table(name="tbl_products")
data class Product(
    @Id
    @Column(name="id_producto")
    var id:String?,

    @Column(name="nombre")
    var name:String,

    @Column(name="marca")
    var branch:String,

    @ManyToOne
    @JoinColumn(name="id_categoria")
    var category: Category?
):Serializable
