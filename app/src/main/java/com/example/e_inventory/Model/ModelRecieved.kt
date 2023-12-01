package com.example.e_inventory.Model

class ModelRecieved(
    val Recieved : List<dataRecieved>

) {
    data class dataRecieved(
        var id : String,
        var date_in : String,
        var qty : String,
        var description : String,
        var product_id : String,
        var product : String,
        var stok : Int,
        var price : Float,
        var category_id : String,
        var category : String,
        var supplier_id : String,
        var supplier : String,
        var supplier_address : String,
        var supplier_phone : String,
        var admin : String
    )
}