package com.example.e_inventory.UI.Supplier

import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import androidx.appcompat.app.AlertDialog
import com.example.e_inventory.API.RetrofitClient
import com.example.e_inventory.Model.ModelResponse
import com.example.e_inventory.R
import com.example.e_inventory.UI.Product.ProductActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailSupplierActivity : AppCompatActivity() {
    private val id by lazy { intent.getStringExtra("id") }
    private val NamaSupplier by lazy { intent.getStringExtra("name") }
    private val Phone by lazy { intent.getStringExtra("phone") }
    private val Alamat by lazy { intent.getStringExtra("alamat") }

    private lateinit var EdNamaSupplier : EditText
    private lateinit var EdPhone : EditText
    private lateinit var EdAlamat : EditText
    private lateinit var BtnSimpan : Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_supplier)

        val BtnBack = findViewById<LinearLayout>(R.id.BtnBack)
        BtnBack.setOnClickListener {
            startActivity(Intent(this, SupplierActivity::class.java))
        }

        EdNamaSupplier = findViewById(R.id.EdNamaSupplier)
        EdPhone = findViewById(R.id.EdNoSupplier)
        EdAlamat = findViewById(R.id.EdAlamat)
        BtnSimpan = findViewById(R.id.BtnSimpan)

        EdNamaSupplier.setText(NamaSupplier)
        EdAlamat.setText(Alamat)
        EdPhone.setText(Phone)


        BtnSimpan.setOnClickListener {
            UpdateSupplier()
        }
    }

    private fun UpdateSupplier() {
        RetrofitClient.instance.EditSupplier(
            id.toString(),
            EdNamaSupplier.text.toString(),
            EdAlamat.text.toString(),
            EdPhone.text.toString()
        ).enqueue(object  : Callback<ModelResponse>{
            override fun onFailure(call: Call<ModelResponse>, t: Throwable) {
                Toast.makeText(this@DetailSupplierActivity, "Maaf Sistem Sedang Gangguan", Toast.LENGTH_SHORT).show()
                Log.e("Kesalahan API Update Supplier : ", t.toString())
            }

            override fun onResponse(call: Call<ModelResponse>, response: Response<ModelResponse>) {
                if (response.isSuccessful){
                    AlertDialog.Builder(this@DetailSupplierActivity)
                        .setTitle("Apakah Anda Yakin Ingin Merubah Data?")
                        .setPositiveButton("Ya", DialogInterface.OnClickListener { dialog, which ->
                            Toast.makeText(this@DetailSupplierActivity, "Berhasil", Toast.LENGTH_SHORT).show()
                        })
                        .setNegativeButton("Tidak", DialogInterface.OnClickListener { dialog, which ->  })
                        .show()
                }else{
                    Toast.makeText(this@DetailSupplierActivity, "Kesalahan", Toast.LENGTH_SHORT).show()
                }
            }

        })
    }
}
