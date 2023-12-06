package com.example.e_inventory.UI.Category

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
import com.example.e_inventory.UI.Beranda.BerandaActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AddCategoryActivity : AppCompatActivity() {

    private lateinit var Kategori : EditText
    private lateinit var BtnSimpan : Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_category)

        Kategori = findViewById(R.id.EdNamaKategori)
        BtnSimpan = findViewById(R.id.BtnSimpan)

        val BtnBack = findViewById<LinearLayout>(R.id.BtnBack)
        BtnBack.setOnClickListener {
            startActivity(Intent(this, BerandaActivity::class.java))
        }

        BtnSimpan.setOnClickListener {
            RetrofitClient.instance.AddCategory(
                Kategori.text.toString()
            ).enqueue(object : Callback<ModelResponse> {
                override fun onFailure(call: Call<ModelResponse>, t: Throwable) {
                    Toast.makeText(this@AddCategoryActivity, "Maaf Sistem Sedang Gangguan", Toast.LENGTH_SHORT).show()
                    Log.e("Kesalahan API Add Kategori : ", t.toString())
                }

                override fun onResponse(
                    call: Call<ModelResponse>,
                    response: Response<ModelResponse>
                ) {
                    AlertDialog.Builder(this@AddCategoryActivity)
                        .setTitle("Tambah Data?")
                        .setPositiveButton("Ya", DialogInterface.OnClickListener { dialog, which ->
                            Toast.makeText(this@AddCategoryActivity, "Berhasil", Toast.LENGTH_SHORT).show()
                            startActivity(Intent(this@AddCategoryActivity, BerandaActivity::class.java))

                        })
                        .setNegativeButton("Tidak", DialogInterface.OnClickListener { dialog, which ->  })
                        .show()

                }

            })
        }
    }
}
