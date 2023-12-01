package com.example.e_inventory.Model

class ModelProduct (
    val Product : List<dataProduct>

) {
    data class dataProduct(
        var id : String,
        var category_id : String,
        var product_id : String,
        var name : String,
        var stok : Int,
        var price : Float,
        var description : String,
        var category : String,
        var supplier : String,
        var supplier_address : String,
        var supplier_phone : String,
        var status : String
    )
}