package com.example.e_inventory.UI.Category

import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.example.e_inventory.API.RetrofitClient
import com.example.e_inventory.Model.ModelResponse
import com.example.e_inventory.R
import com.example.e_inventory.UI.Login.LoginActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailKategoriActivity : AppCompatActivity() {

    private val NamaCategory by lazy { intent.getStringExtra("namaKategori") }
    private val CategoryId by lazy { intent.getStringExtra("categoryId") }
    private val StatusCategory by lazy { intent.getStringExtra("statusCategory") }
    private lateinit var Kategori : EditText
    private lateinit var Status : EditText
    private lateinit var BtnSimpan : Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_kategori)
        Kategori = findViewById(R.id.EdNamaKategori)
        Status = findViewById(R.id.EdStatus)
        BtnSimpan = findViewById(R.id.BtnSimpan)

        Kategori.setText(NamaCategory)
        Status.setText(StatusCategory)

        BtnSimpan.setOnClickListener {
            RetrofitClient.instance.EditCategory(
                CategoryId.toString(),
                Kategori.text.toString(),
                Status.text.toString()
            ).enqueue(object : Callback<ModelResponse>{
                override fun onFailure(call: Call<ModelResponse>, t: Throwable) {
                    Toast.makeText(this@DetailKategoriActivity, "Maaf Sistem Sedang Gangguan", Toast.LENGTH_SHORT).show()
                    Log.e("Kesalahan API Edit Kategori : ", t.toString())
                }

                override fun onResponse(
                    call: Call<ModelResponse>,
                    response: Response<ModelResponse>
                ) {
                    AlertDialog.Builder(this@DetailKategoriActivity)
                        .setTitle("Apakah Anda Yakin Ingin Merubah Data?")
                        .setPositiveButton("Ya", DialogInterface.OnClickListener { dialog, which ->
                            Toast.makeText(this@DetailKategoriActivity, "Berhasil", Toast.LENGTH_SHORT).show()

                        })
                        .setNegativeButton("Tidak", DialogInterface.OnClickListener { dialog, which ->  })
                        .show()

                }

            })
        }
    }
}
