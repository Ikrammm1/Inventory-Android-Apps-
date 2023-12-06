package com.example.e_inventory.UI.Profile

import android.content.DialogInterface
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.example.e_inventory.API.RetrofitClient
import com.example.e_inventory.Model.ModelResponse
import com.example.e_inventory.R
import com.example.e_inventory.UI.Login.LoginActivity
import kotlinx.android.synthetic.main.activity_profile.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ProfileActivity : AppCompatActivity() {

    private lateinit var Email : EditText
    private lateinit var Password : EditText
    private lateinit var NamaUser : EditText
    private lateinit var Phone : EditText
    private lateinit var Alamat : EditText
    private lateinit var BtnSimpan : Button
    private lateinit var Role : String
    private lateinit var profil : SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)
        profil = getSharedPreferences("Login_Session", MODE_PRIVATE)
        Email = findViewById(R.id.EdEmail)
        Password = findViewById(R.id.EdPass)
        NamaUser = findViewById(R.id.EdNamaUser)
        Phone = findViewById(R.id.EdPhone)
        Alamat = findViewById(R.id.EdAlamat)
        BtnSimpan = findViewById(R.id.BtnSimpan)
        val Nama = findViewById<TextView>(R.id.txtNama)
        val TxtEmail = findViewById<TextView>(R.id.txtEmail)
        Nama.setText(profil.getString("name", null).toString())
        TxtEmail.setText(profil.getString("email", null).toString())


        if (profil.getString("role", null).toString() == "Admin"){
            Role = "1"
        }else{
            Role = "2"
        }

        Email.setText(profil.getString("email", null).toString())
        NamaUser.setText(profil.getString("name", null).toString())
        Phone.setText(profil.getString("phone", null).toString())
        Alamat.setText(profil.getString("address", null).toString())

        BtnSimpan.setOnClickListener {
            AlertDialog.Builder(this)
                .setTitle("Apakah Anda Yakin Ingin Mengubah Data?")
                .setPositiveButton("Ya", DialogInterface.OnClickListener { dialog, which ->

                    RetrofitClient.instance.EditUser(
                        profil.getString("id", null).toString(),
                        NamaUser.text.toString(),
                        Email.text.toString(),
                        Password.text.toString(),
                        Alamat.text.toString(),
                        Role,
                        Phone.text.toString()
                    ).enqueue(object : Callback<ModelResponse>{
                        override fun onFailure(call: Call<ModelResponse>, t: Throwable) {
                            Toast.makeText(this@ProfileActivity, "Gagal", Toast.LENGTH_LONG).show()
                            Log.e("kesalahan API Edit : ", t.toString())
                            Log.d("id", profil.getString("id", null).toString())
                            Log.d("name", NamaUser.text.toString())
                            Log.d("email", Email.text.toString())
                            Log.d("password", Password.text.toString())
                            Log.d("address", Alamat.text.toString())
                            Log.d("role",Role)
                            Log.d("phone", Phone.text.toString())
                        }

                        override fun onResponse(
                            call: Call<ModelResponse>,
                            response: Response<ModelResponse>
                        ) {
                            Toast.makeText(this@ProfileActivity, "Berhasil, Silahkan Login Ulang", Toast.LENGTH_LONG).show()

                            //menghapus session
                            profil.edit().clear().commit()
                            startActivity(Intent(this@ProfileActivity, LoginActivity::class.java))

                        }

                    })
                })
                .setNegativeButton("Tidak", DialogInterface.OnClickListener { dialog, which ->  })
                .show()

        }

    }
}
