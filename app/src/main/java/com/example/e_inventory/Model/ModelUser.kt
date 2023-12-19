package com.example.e_inventory.Model

class ModelUser(
    val Users : List<dataUsers>
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
}