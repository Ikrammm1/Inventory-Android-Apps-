package com.example.e_inventory.UI.Shipped

import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.e_inventory.API.RetrofitClient
import com.example.e_inventory.Adapter.AdapterReceived
import com.example.e_inventory.Adapter.AdapterShipped
import com.example.e_inventory.Model.ModelRecieved
import com.example.e_inventory.Model.ModelResponse
import com.example.e_inventory.Model.ModelShipped
import com.example.e_inventory.R
import com.example.e_inventory.UI.Beranda.BerandaActivity
import com.example.e_inventory.UI.Recieved.AddReceivedActivity
import com.example.e_inventory.UI.Recieved.EditReceivedActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ShippeedActivity : AppCompatActivity() {

    lateinit var ListShipped : RecyclerView
    lateinit var Adapter : AdapterShipped
    lateinit var BtnAdd : ImageView
    lateinit var TotalShipped : TextView
    private lateinit var swipeRefreshLayout: SwipeRefreshLayout


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_shippeed)
        TotalShipped = findViewById(R.id.TotalShipped)
        val BtnBack = findViewById<LinearLayout>(R.id.BtnBack)

        BtnBack.setOnClickListener {
            startActivity(Intent(this, BerandaActivity::class.java))
        }

        getShipped()
        setUpListShipped()

        swipeRefreshLayout = findViewById(R.id.swipeRefreshLayout)
        swipeRefreshLayout.setOnRefreshListener {
            getShipped()
            setUpListShipped()
            swipeRefreshLayout.isRefreshing = false
        }

        BtnAdd = findViewById(R.id.btnAdd)

        BtnAdd.setOnClickListener {
            startActivity(Intent(this, AddShippedActivity::class.java))
        }
    }

    private fun getShipped() {
        RetrofitClient.instance.Shipped().enqueue(object : Callback<ModelShipped> {
            override fun onFailure(call: Call<ModelShipped>, t: Throwable) {
                Toast.makeText(this@ShippeedActivity, "Maaf Sistem Sedang Gangguan", Toast.LENGTH_SHORT).show()
                Log.e("Kesalahan API Shipped : ", t.toString())
            }

            override fun onResponse(call: Call<ModelShipped>, response: Response<ModelShipped>) {
                if (response.isSuccessful){
                    TotalShipped.text = response.body()!!.Shipped.size.toString()
                    val ListData = response.body()!!.Shipped
                    ListData.forEach {
                        Adapter.setData(ListData)
                    }
                } else{
                    Toast.makeText(this@ShippeedActivity, "Maaf Terjadi Kesalahan", Toast.LENGTH_SHORT).show()
                }
            }

        })
    }

    private fun setUpListShipped(){
        ListShipped = findViewById(R.id.ListShipped)
        Adapter = AdapterShipped(arrayListOf(), object : AdapterShipped.OnAdapterlistener{
            override fun onClick(shipped: ModelShipped.dataShipped) {
                startActivity(
                    Intent(this@ShippeedActivity, EditShippedActivity::class.java)
                        .putExtra("id", shipped.id)
                        .putExtra("product", shipped.product)
                        .putExtra("qty", shipped.qty)
                        .putExtra("description", shipped.description)
                        .putExtra("tglKeluar", shipped.date_out)
                        .putExtra("kategori", shipped.category)
                        .putExtra("supplier", shipped.supplier)
                        .putExtra("category_id", shipped.category_id)
                        .putExtra("product_id", shipped.product_id)
                        .putExtra("admin", shipped.admin)
                )
            }

            override fun onDelete(shipped: ModelShipped.dataShipped) {
                RetrofitClient.instance.DeleteShipped(shipped.id, shipped.product_id)
                    .enqueue(object : Callback<ModelResponse> {
                        override fun onFailure(call: Call<ModelResponse>, t: Throwable) {
                            Toast.makeText(this@ShippeedActivity, "Maaf Sistem Sedang Gangguan", Toast.LENGTH_SHORT).show()
                            Log.e("Kesalahan API Shipped : ", t.toString())
                        }

                        override fun onResponse(
                            call: Call<ModelResponse>,
                            response: Response<ModelResponse>
                        ) {
                            if (response.isSuccessful){
                                AlertDialog.Builder(this@ShippeedActivity)
                                    .setTitle("Apakah Anda Yakin Ingin Hapus Data?")
                                    .setPositiveButton("Ya", DialogInterface.OnClickListener { dialog, which ->
                                        Toast.makeText(this@ShippeedActivity, "Berhasil", Toast.LENGTH_SHORT).show()
                                        getShipped()
                                    })
                                    .setNegativeButton("Tidak", DialogInterface.OnClickListener { dialog, which ->  })
                                    .show()

                            }else{
                                Toast.makeText(this@ShippeedActivity, "Kesalahan", Toast.LENGTH_SHORT).show()
                            }
                        }

                    })
            }

        })
        ListShipped.adapter = Adapter

    }
}
