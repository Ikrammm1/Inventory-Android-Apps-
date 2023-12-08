package com.example.e_inventory.UI.Recieved

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
import com.example.e_inventory.API.RetrofitClient
import com.example.e_inventory.Adapter.AdapterReceived
import com.example.e_inventory.Adapter.AdapterSupplier
import com.example.e_inventory.Model.ModelRecieved
import com.example.e_inventory.Model.ModelResponse
import com.example.e_inventory.Model.ModelSupplier
import com.example.e_inventory.R
import com.example.e_inventory.UI.Beranda.BerandaActivity
import com.example.e_inventory.UI.Supplier.AddSupplierActivity
import com.example.e_inventory.UI.Supplier.DetailSupplierActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RecievedActivity : AppCompatActivity() {

    lateinit var ListReceived : RecyclerView
    lateinit var Adapter : AdapterReceived
    lateinit var BtnAdd : ImageView
    lateinit var TotalReceived : TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recieved)

        TotalReceived = findViewById(R.id.TotalReceived)
        val BtnBack = findViewById<LinearLayout>(R.id.BtnBack)

        BtnBack.setOnClickListener {
            startActivity(Intent(this, BerandaActivity::class.java))
        }

        getReceived()
        setUpListReceived()


        BtnAdd = findViewById(R.id.btnAdd)

        BtnAdd.setOnClickListener {
            startActivity(Intent(this, AddReceivedActivity::class.java))
        }
    }

    private fun getReceived() {
        RetrofitClient.instance.Recieved().enqueue(object : Callback<ModelRecieved>{
            override fun onFailure(call: Call<ModelRecieved>, t: Throwable) {
                Toast.makeText(this@RecievedActivity, "Maaf Sistem Sedang Gangguan", Toast.LENGTH_SHORT).show()
                Log.e("Kesalahan API Received : ", t.toString())
            }

            override fun onResponse(call: Call<ModelRecieved>, response: Response<ModelRecieved>) {
                if (response.isSuccessful){
                    TotalReceived.text = response.body()!!.Recieved.size.toString()
                    val ListData = response.body()!!.Recieved
                    ListData.forEach {
                        Adapter.setData(ListData)
                    }
                } else{
                    Toast.makeText(this@RecievedActivity, "Maaf Terjadi Kesalahan", Toast.LENGTH_SHORT).show()
                }
            }

        })
    }

    private fun setUpListReceived(){
        ListReceived = findViewById(R.id.ListReceived)
        Adapter = AdapterReceived(arrayListOf(), object : AdapterReceived.OnAdapterlistener{
            override fun onClick(received: ModelRecieved.dataRecieved) {
                startActivity(Intent(this@RecievedActivity, EditReceivedActivity::class.java)
                    .putExtra("id", received.id)
                    .putExtra("product", received.product)
                    .putExtra("qty", received.qty)
                    .putExtra("alamat", received.supplier_address)
                    .putExtra("description", received.description)
                    .putExtra("phone", received.supplier_phone)
                    .putExtra("tglMasuk", received.date_in)
                    .putExtra("kategori", received.category)
                    .putExtra("supplier", received.supplier)
                    .putExtra("category_id", received.category_id)
                    .putExtra("product_id", received.product_id)
                    .putExtra("supplier_id", received.supplier_id)
                    .putExtra("admin", received.admin)
                )
            }

            override fun onDelete(received: ModelRecieved.dataRecieved) {
                RetrofitClient.instance.DeleteSupplier(received.id)
                    .enqueue(object : Callback<ModelResponse>{
                        override fun onFailure(call: Call<ModelResponse>, t: Throwable) {
                            Toast.makeText(this@RecievedActivity, "Maaf Sistem Sedang Gangguan", Toast.LENGTH_SHORT).show()
                            Log.e("Kesalahan API Received : ", t.toString())
                        }

                        override fun onResponse(
                            call: Call<ModelResponse>,
                            response: Response<ModelResponse>
                        ) {
                            if (response.isSuccessful){
                                AlertDialog.Builder(this@RecievedActivity)
                                    .setTitle("Apakah Anda Yakin Ingin Hapus Data?")
                                    .setPositiveButton("Ya", DialogInterface.OnClickListener { dialog, which ->
                                        Toast.makeText(this@RecievedActivity, "Berhasil", Toast.LENGTH_SHORT).show()
                                        getReceived()
                                    })
                                    .setNegativeButton("Tidak", DialogInterface.OnClickListener { dialog, which ->  })
                                    .show()

                            }else{
                                Toast.makeText(this@RecievedActivity, "Kesalahan", Toast.LENGTH_SHORT).show()
                            }
                        }

                    })
            }

        })
        ListReceived.adapter = Adapter

    }
}
