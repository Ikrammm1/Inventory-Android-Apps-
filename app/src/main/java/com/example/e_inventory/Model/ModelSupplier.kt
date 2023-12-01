package com.example.e_inventory.Model

class ModelSupplier (
    val Supplier : List<dataSupplier>

) {
    data class dataSupplier(
        var id : String,
        var name : String,
        var address : String,
        var phone_number : String,
        var status : String
    )

}