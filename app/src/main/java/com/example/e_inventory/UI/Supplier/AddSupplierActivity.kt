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
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AddSupplierActivity : AppCompatActivity() {

    private lateinit var EdNamaSupplier : EditText
    private lateinit var EdPhone : EditText
    private lateinit var EdAlamat : EditText
    private lateinit var BtnSimpan : Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_supplier)

        EdNamaSupplier = findViewById(R.id.EdNamaSupplier)
        EdPhone = findViewById(R.id.EdNoSupplier)
        EdAlamat = findViewById(R.id.EdAlamat)
        BtnSimpan = findViewById(R.id.BtnSimpan)

        val BtnBack = findViewById<LinearLayout>(R.id.BtnBack)
        BtnBack.setOnClickListener {
            finish()
        }

        BtnSimpan.setOnClickListener {
            AddSupplier()
        }
    }

    private fun AddSupplier() {
        RetrofitClient.instance.AddSupplier(
            EdNamaSupplier.text.toString(),
            EdAlamat.text.toString(),
            EdPhone.text.toString()
        ).enqueue(object : Callback<ModelResponse>{
            override fun onFailure(call: Call<ModelResponse>, t: Throwable) {
                Toast.makeText(this@AddSupplierActivity, "Maaf Sistem Sedang Gangguan", Toast.LENGTH_SHORT).show()
                Log.e("Kesalahan API Add Supplier : ", t.toString())
            }

            override fun onResponse(call: Call<ModelResponse>, response: Response<ModelResponse>) {
                if (response.isSuccessful){
                    AlertDialog.Builder(this@AddSupplierActivity)
                        .setTitle("Tambah Data?")
                        .setPositiveButton("Ya", DialogInterface.OnClickListener { dialog, which ->
                            Toast.makeText(this@AddSupplierActivity, "Berhasil", Toast.LENGTH_SHORT).show()
                            startActivity(Intent(this@AddSupplierActivity, SupplierActivity::class.java))
                        })
                        .setNegativeButton("Tidak", DialogInterface.OnClickListener { dialog, which ->  })
                        .show()
                }else{
                    Toast.makeText(this@AddSupplierActivity, "Kesalahan", Toast.LENGTH_SHORT).show()
                }
            }

        })
    }
}
