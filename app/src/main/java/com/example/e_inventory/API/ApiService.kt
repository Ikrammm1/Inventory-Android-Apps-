package com.example.e_inventory.API

import com.example.e_inventory.Model.*
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST

interface ApiService {
    @FormUrlEncoded
    @POST("login.php")
    fun login(
        @Field("email") email : String,
        @Field("password") password : String
    ) : Call<ResponseLogin>

    @GET("ListUsers.php")
    fun Users():Call<ModelUser>

    @GET("ListCategory.php")
    fun Category():Call<ModelCategory>

    @GET("ListSupplier.php")
    fun Supplier():Call<ModelSupplier>

    @GET("ListProduct.php")
    fun Product():Call<ModelProduct>

    @GET("ListRecieved.php")
    fun Recieved():Call<ModelRecieved>

    @GET("ListShipped.php")
    fun Shipped():Call<ModelShipped>

    @FormUrlEncoded
    @POST("AddUser.php")
    fun AddUser(
        @Field("name") name : String,
        @Field("email") email : String,
        @Field("password") password : String,
        @Field("address") address : String,
        @Field("role") role : String,
        @Field("phone_number") phone_number : String
    ) : Call<ModelResponse>

    @FormUrlEncoded
    @POST("AddCategory.php")
    fun AddCategory(
        @Field("name") name : String
    ) : Call<ModelResponse>

    @FormUrlEncoded
    @POST("AddSupplier.php")
    fun AddSupplier(
        @Field("name") name : String,
        @Field("address") address : String,
        @Field("phone_number") phone_number : String
    ) : Call<ModelResponse>

    @FormUrlEncoded
    @POST("AddProduct.php")
    fun AddProduct(
        @Field("category_id") category_id : String,
        @Field("supplier_id") supplier_id : String,
        @Field("name") name : String,
        @Field("stok") stok : String,
        @Field("price") price : String,
        @Field("description") description : String
    ) : Call<ModelResponse>

    @FormUrlEncoded
    @POST("AddBarangMasuk.php")
    fun AddBarangMasuk(
        @Field("dateIn") dateIn : String,
        @Field("admin") admin : String,
        @Field("product_id") product_id : String,
        @Field("qty") qty : String,
        @Field("description") description : String
    ) : Call<ModelResponse>

    @FormUrlEncoded
    @POST("AddBarangKeluar.php")
    fun AddBarangKeluar(
        @Field("dateOut") dateOut : String,
        @Field("admin") admin : String,
        @Field("product_id") product_id : String,
        @Field("qty") qty : String,
        @Field("description") description : String
    ) : Call<ModelResponse>

    @FormUrlEncoded
    @POST("UpdateBarangKeluar.php")
    fun EditBarangKeluar(
        @Field("id") id : String,
        @Field("dateOut") dateOut : String,
        @Field("admin") admin : String,
        @Field("product_id") product_id : String,
        @Field("qty") qty : String,
        @Field("description") description : String
    ) : Call<ModelResponse>

    @FormUrlEncoded
    @POST("UpdateBarangMasuk.php")
    fun EditBarangMasuk(
        @Field("id") id : String,
        @Field("dateIn") dateIn : String,
        @Field("admin") admin : String,
        @Field("product_id") product_id : String,
        @Field("qty") qty : String,
        @Field("description") description : String
    ) : Call<ModelResponse>

    @FormUrlEncoded
    @POST("UpdateProduct.php")
    fun EditProduct(
        @Field("id") id : String,
        @Field("category_id") category_id : String,
        @Field("supplier_id") supplier_id : String,
        @Field("name") name : String,
        @Field("stok") stok : String,
        @Field("price") price : String,
        @Field("description") description : String
    ) : Call<ModelResponse>

    @FormUrlEncoded
    @POST("UpdateSupplier.php")
    fun EditSupplier(
        @Field("id") id : String,
        @Field("name") name : String,
        @Field("address") address : String,
        @Field("phone_number") phone_number : String
    ) : Call<ModelResponse>

    @FormUrlEncoded
    @POST("UpdateCategory.php")
    fun EditCategory(
        @Field("category_id") category_id : String,
        @Field("name") name : String,
        @Field("status") status : String
    ) : Call<ModelResponse>

    @FormUrlEncoded
    @POST("UpdateUser.php")
    fun EditUser(
        @Field("id") id : String,
        @Field("name") name : String,
        @Field("email") email : String,
        @Field("password") password : String,
        @Field("address") address : String,
        @Field("role") role : String,
        @Field("phone_number") phone_number : String
        ) : Call<ModelResponse>

    @FormUrlEncoded
    @POST("DeleteCategory.php")
    fun DeleteCategory(
        @Field("category_id") category_id : String
    ) : Call<ModelResponse>

    @FormUrlEncoded
    @POST("DeleteUser.php")
    fun DeleteUser(
        @Field("id") id : String
    ) : Call<ModelResponse>

    @FormUrlEncoded
    @POST("DeleteSupplier.php")
    fun DeleteSupplier(
        @Field("id") id : String
    ) : Call<ModelResponse>

    @FormUrlEncoded
    @POST("DeleteProduct.php")
    fun DeleteProduct(
        @Field("id") id : String
    ) : Call<ModelResponse>

    @FormUrlEncoded
    @POST("DeleteRecieve.php")
    fun DeleteRecieved(
        @Field("id") id : String
    ) : Call<ModelResponse>

    @FormUrlEncoded
    @POST("DeleteShipped.php")
    fun DeleteShipped(
        @Field("id") id : String
    ) : Call<ModelResponse>



}