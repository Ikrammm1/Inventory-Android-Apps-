package com.example.e_inventory.UI.User

import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.example.e_inventory.API.RetrofitClient
import com.example.e_inventory.Model.ModelResponse
import com.example.e_inventory.R
import com.example.e_inventory.UI.Login.LoginActivity
import com.example.e_inventory.UI.Supplier.SupplierActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UpdateUserActivity : AppCompatActivity() {

    private val id by lazy { intent.getStringExtra("id") }
    private val Nama by lazy { intent.getStringExtra("name") }
    private val Phone by lazy { intent.getStringExtra("phone") }
    private val Alamat by lazy { intent.getStringExtra("alamat") }
    private val Email by lazy { intent.getStringExtra("email") }
    private val role by lazy { intent.getStringExtra("role") }


    private lateinit var EdEmail : EditText
    private lateinit var EdPassword : EditText
    private lateinit var EdNamaUser : EditText
    private lateinit var EdPhone : EditText
    private lateinit var EdAlamat : EditText
    private lateinit var BtnSimpan : Button
    private lateinit var Role : String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_update_user)

        val BtnBack = findViewById<LinearLayout>(R.id.BtnBack)
        BtnBack.setOnClickListener {
            startActivity(Intent(this, UserActivity::class.java))
        }

        EdEmail = findViewById(R.id.EdEmail)
        EdPassword = findViewById(R.id.EdPass)
        EdNamaUser = findViewById(R.id.EdNama)
        EdPhone = findViewById(R.id.EdPhone)
        EdAlamat = findViewById(R.id.EdAlamat)
        BtnSimpan = findViewById(R.id.BtnSimpan)

        EdNamaUser.setText(Nama)
        EdEmail.setText(Email)
        EdAlamat.setText(Alamat)
        EdPhone.setText(Phone)

        if (role.toString() == "Admin"){
            Role = "1"
        }else{
            Role = "2"
        }
        BtnSimpan.setOnClickListener {
            AlertDialog.Builder(this)
                .setTitle("Apakah Anda Yakin Ingin Mengubah Data?")
                .setPositiveButton("Ya", DialogInterface.OnClickListener { dialog, which ->
                    UpdateUser()
                })
                .setNegativeButton("Tidak", DialogInterface.OnClickListener { dialog, which ->  })
                .show()
        }


    }

    private fun UpdateUser() {
        RetrofitClient.instance.EditUser(
            id.toString(),
            EdNamaUser.text.toString(),
            EdEmail.text.toString(),
            EdPassword.text.toString(),
            EdAlamat.text.toString(),
            Role,
            EdPhone.text.toString()
            ).enqueue(object : Callback<ModelResponse>{
            override fun onFailure(call: Call<ModelResponse>, t: Throwable) {
                Toast.makeText(this@UpdateUserActivity, "Maaf Sistem sedang gangguan", Toast.LENGTH_LONG).show()
                Log.e("kesalahan API Edit : ", t.toString())
            }

            override fun onResponse(call: Call<ModelResponse>, response: Response<ModelResponse>) {
               if (response.isSuccessful){
                   Toast.makeText(this@UpdateUserActivity, "Berhasil", Toast.LENGTH_LONG).show()
               }else{
                   Toast.makeText(this@UpdateUserActivity, "Kesalahan", Toast.LENGTH_LONG).show()
               }
            }

        })
    }
}
