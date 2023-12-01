package com.example.e_inventory.Model

class ModelList (
    val User : List<dataUsers>,
    val Category : List<dataCategory>,
    val Supplier : List<dataSupplier>,
    val Product : List<dataProduct>,
    val Recieved : List<dataRecieved>,
    val Shipped : List<dataShipped>

) {
    data class dataUsers(
        var id : String,
        var name : String,
        var email : String,
        var password : String,
        var address : String,
        var role : String,
        var phone_number : String
    )

    data class dataCategory(
        var category_id : String,
        var name : String,
        var status : String
    )

    data class dataSupplier(
        var id : String,
        var name : String,
        var address : String,
        var phone_number : String,
        var status : String
    )

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

    data class dataShipped(
        var id : String,
        var date_out : String,
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