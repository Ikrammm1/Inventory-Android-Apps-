package com.example.e_inventory.Model

class ModelCategory (
    val Category : List<dataCategory>

) {
    data class dataCategory(
        var category_id : String,
        var name : String,
        var status : String
    )

}