package edu.eam.ingesoft.onlinestore.model.entities

import java.io.Serializable
import javax.persistence.*

@Entity
@Table(name="tbl_productos_tienda")
data class ProductStore(
    @Id
    @Column(name="id")
    val id:Long,

    @Column(name="precio")
    var price:Double,

    @Column(name="cantidad")
    var stock:Double,

    @ManyToOne
    @JoinColumn(name="id_producto")
    val product: Product,

    @ManyToOne
    @JoinColumn(name="id_tienda")
    val store: Store,
):Serializable
