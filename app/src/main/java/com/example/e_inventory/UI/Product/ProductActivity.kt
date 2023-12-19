package com.example.e_inventory.UI.Product

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.e_inventory.API.RetrofitClient
import com.example.e_inventory.Adapter.AdapterCategory
import com.example.e_inventory.Adapter.AdapterProduct
import com.example.e_inventory.Model.ModelProduct
import com.example.e_inventory.R
import com.example.e_inventory.UI.Beranda.BerandaActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ProductActivity : AppCompatActivity() {

    lateinit var ListProduct : RecyclerView
    lateinit var Adapter : AdapterProduct
    lateinit var BtnAdd : ImageView
    private var gridLayoutManager: GridLayoutManager? = null
    private lateinit var swipeRefreshLayout: SwipeRefreshLayout


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product)

        val BtnBack = findViewById<LinearLayout>(R.id.BtnBack)
        BtnBack.setOnClickListener {
            startActivity(Intent(this, BerandaActivity::class.java))
        }

        getProduct()
        setUpListProduct()

        swipeRefreshLayout = findViewById(R.id.swipeRefreshLayout)
        swipeRefreshLayout.setOnRefreshListener {
            getProduct()
            setUpListProduct()
            swipeRefreshLayout.isRefreshing = false
        }

        BtnAdd = findViewById(R.id.btnAdd)

        BtnAdd.setOnClickListener {
            startActivity(Intent(this, AddProductActivity::class.java))
        }


    }
    private fun getProduct(){
        RetrofitClient.instance.Product().enqueue(object :Callback<ModelProduct>{
            override fun onFailure(call: Call<ModelProduct>, t: Throwable) {
                Toast.makeText(this@ProductActivity, "Maaf Sistem Sedang Gangguan", Toast.LENGTH_SHORT).show()
                Log.e("Kesalahan API Product : ", t.toString())
            }

            override fun onResponse(call: Call<ModelProduct>, response: Response<ModelProduct>) {
                if (response.isSuccessful){
                    val ListData = response.body()!!.Product
                    ListData.forEach {
                        Adapter.setData(ListData)
                    }
                } else{
                    Toast.makeText(this@ProductActivity, "Maaf Terjadi Kesalahan", Toast.LENGTH_SHORT).show()
                }
            }

        })
    }
    private fun setUpListProduct(){
        ListProduct = findViewById(R.id.ListProduct)
        gridLayoutManager = GridLayoutManager(applicationContext, 2, LinearLayoutManager.VERTICAL, false)
        ListProduct.layoutManager = gridLayoutManager
        Adapter = AdapterProduct(arrayListOf(), object : AdapterProduct.OnAdapterlistener{
            override fun onClick(product: ModelProduct.dataProduct) {
               startActivity(Intent(this@ProductActivity, DetailProductActivity::class.java)
                   .putExtra("id", product.id)
                   .putExtra("name", product.name)
                   .putExtra("stok", product.stok)
                   .putExtra("price", product.price)
                   .putExtra("description", product.description)
                   .putExtra("category_id", product.category_id)
                   .putExtra("category", product.category)
                   .putExtra("supplier_id", product.supplier_id)
                   .putExtra("supplier", product.supplier)
                   .putExtra("supplier_address", product.supplier_address)
                   .putExtra("supplier_phone", product.supplier_phone)
                   .putExtra("status", product.status)
               )
            }

        })
        ListProduct.adapter = Adapter

    }
}
