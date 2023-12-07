package com.example.e_inventory.UI.Supplier

import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.e_inventory.API.RetrofitClient
import com.example.e_inventory.Adapter.AdapterProduct
import com.example.e_inventory.Adapter.AdapterSupplier
import com.example.e_inventory.Model.ModelProduct
import com.example.e_inventory.Model.ModelResponse
import com.example.e_inventory.Model.ModelSupplier
import com.example.e_inventory.R
import com.example.e_inventory.UI.Beranda.BerandaActivity
import com.example.e_inventory.UI.Product.AddProductActivity
import com.example.e_inventory.UI.Product.DetailProductActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SupplierActivity : AppCompatActivity() {
    lateinit var ListSupplier : RecyclerView
    lateinit var Adapter : AdapterSupplier
    lateinit var BtnAdd : ImageView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_supplier)
        val BtnBack = findViewById<LinearLayout>(R.id.BtnBack)
        getSupplier()
        setUpListSupplier()
        BtnBack.setOnClickListener {
            startActivity(Intent(this, BerandaActivity::class.java))
        }
        BtnAdd = findViewById(R.id.btnAdd)

        BtnAdd.setOnClickListener {
            startActivity(Intent(this, AddSupplierActivity::class.java))
        }
    }
    private fun getSupplier(){
        RetrofitClient.instance.Supplier().enqueue(object : Callback<ModelSupplier> {
            override fun onFailure(call: Call<ModelSupplier>, t: Throwable) {
                Toast.makeText(this@SupplierActivity, "Maaf Sistem Sedang Gangguan", Toast.LENGTH_SHORT).show()
                Log.e("Kesalahan API Supplier : ", t.toString())
            }

            override fun onResponse(call: Call<ModelSupplier>, response: Response<ModelSupplier>) {
                if (response.isSuccessful){
                    val ListData = response.body()!!.Supplier
                    ListData.forEach {
                        Adapter.setData(ListData)
                    }
                } else{
                    Toast.makeText(this@SupplierActivity, "Maaf Terjadi Kesalahan", Toast.LENGTH_SHORT).show()
                }
            }

        })
    }

    private fun setUpListSupplier(){
        ListSupplier = findViewById(R.id.ListSupplier)
        Adapter = AdapterSupplier(arrayListOf(), object : AdapterSupplier.OnAdapterlistener{
            override fun onClick(supplier: ModelSupplier.dataSupplier) {
                startActivity(Intent(this@SupplierActivity, DetailSupplierActivity::class.java)
                    .putExtra("id", supplier.id)
                    .putExtra("name", supplier.name)
                    .putExtra("phone", supplier.phone_number)
                    .putExtra("alamat", supplier.address)
                    .putExtra("status", supplier.status)
                )
            }

            override fun onDelete(supplier: ModelSupplier.dataSupplier) {
                RetrofitClient.instance.DeleteSupplier(supplier.id)
                    .enqueue(object : Callback<ModelResponse>{
                        override fun onFailure(call: Call<ModelResponse>, t: Throwable) {
                            Toast.makeText(this@SupplierActivity, "Maaf Sistem Sedang Gangguan", Toast.LENGTH_SHORT).show()
                            Log.e("Kesalahan API Supplier : ", t.toString())
                        }

                        override fun onResponse(
                            call: Call<ModelResponse>,
                            response: Response<ModelResponse>
                        ) {
                            if (response.isSuccessful){
                                AlertDialog.Builder(this@SupplierActivity)
                                    .setTitle("Apakah Anda Yakin Ingin Hapus Data?")
                                    .setPositiveButton("Ya", DialogInterface.OnClickListener { dialog, which ->
                                        Toast.makeText(this@SupplierActivity, "Berhasil", Toast.LENGTH_SHORT).show()
                                        getSupplier()
                                    })
                                    .setNegativeButton("Tidak", DialogInterface.OnClickListener { dialog, which ->  })
                                    .show()

                            }else{
                                Toast.makeText(this@SupplierActivity, "Kesalahan", Toast.LENGTH_SHORT).show()
                            }
                        }

                    })
            }

        })
        ListSupplier.adapter = Adapter

    }
}
