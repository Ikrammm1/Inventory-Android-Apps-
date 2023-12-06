package com.example.e_inventory.UI.Login

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.e_inventory.API.RetrofitClient
import com.example.e_inventory.Model.ResponseLogin
import com.example.e_inventory.R
import com.example.e_inventory.UI.Beranda.BerandaActivity
import kotlinx.android.synthetic.main.activity_login.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginActivity : AppCompatActivity() {
    lateinit var Email : EditText
    lateinit var Pass : EditText
    private lateinit var profil : SharedPreferences


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        //cek session
        profil = getSharedPreferences("Login_Session", MODE_PRIVATE)
        if(profil.getString("id",null) !=null) {
            startActivity(Intent(this@LoginActivity, BerandaActivity::class.java))
            finish()
        }

        Email = findViewById(R.id.EdEmail)
        Pass = findViewById(R.id.EdPass)
        val SignIn = findViewById<Button>(R.id.btnSignIn)

        SignIn.setOnClickListener {
            when{
                Email.text.toString() == "" ->{
                    Email.error = "Email Tidak Boleh Kosong!"
                }
                Pass.text.toString() == "" -> {
                    Pass.error = "Password Tidak Boleh Kosong!"
                }
                else -> {
                    Login()
                }
            }
        }
    }
    private fun Login(){
        RetrofitClient.instance.login(
            Email.text.toString(),
            Pass.text.toString()
        ).enqueue(object : Callback<ResponseLogin>{
            override fun onFailure(call: Call<ResponseLogin>, t: Throwable) {
                Toast.makeText(this@LoginActivity, "Gagal", Toast.LENGTH_LONG).show()
                Log.e("kesalahan API Login : ", t.toString())
            }

            override fun onResponse(call: Call<ResponseLogin>, response: Response<ResponseLogin>) {

                if (response.isSuccessful){
                    //fungsi session
                    getSharedPreferences("Login_Session", MODE_PRIVATE)
                        .edit()
                        .putString("id", response.body()?.payload?.id)
                        .putString("name", response.body()?.payload?.name)
                        .putString("email", response.body()?.payload?.email)
                        .putString("password", response.body()?.payload?.password)
                        .putString("address", response.body()?.payload?.address)
                        .putString("phone", response.body()?.payload?.phone_number)
                        .putString("role", response.body()?.payload?.role)
                        .apply()

                    if(response.body()?.response == true) {
                        startActivity(Intent(this@LoginActivity, BerandaActivity::class.java))
                        finish()
                    }else{
                        Toast.makeText(this@LoginActivity, "Email / Password Salah", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    Toast.makeText(this@LoginActivity, "Kesalahan", Toast.LENGTH_SHORT).show()

                }
            }

        })
    }
}
