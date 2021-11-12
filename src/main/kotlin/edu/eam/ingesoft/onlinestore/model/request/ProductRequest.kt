package edu.eam.ingesoft.onlinestore.model.request

data class ProductRequest(
    val id:Long,
    val price:Double,
    val stock:Double,
    val idProduct:String?,
    val idStore:Long?,

)
