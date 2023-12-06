package com.example.e_inventory.Model

class ModelProduct (
    val Product : List<dataProduct>

) {
    data class dataProduct(
        var id : String,
        var category_id : String,
        var supplier_id : String,
        var name : String,
        var stok : String,
        var price : String,
        var description : String,
        var category : String,
        var supplier : String,
        var supplier_address : String,
        var supplier_phone : String,
        var status : String
    )
}