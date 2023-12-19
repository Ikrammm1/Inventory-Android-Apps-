package com.example.e_inventory.UI.User

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

class AddUserActivity : AppCompatActivity() {

    private lateinit var EdEmail : EditText
    private lateinit var EdPassword : EditText
    private lateinit var EdNamaUser : EditText
    private lateinit var EdPhone : EditText
    private lateinit var EdAlamat : EditText
    private lateinit var BtnSimpan : Button
    private lateinit var Role : String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_user)

        val BtnBack = findViewById<LinearLayout>(R.id.BtnBack)
        BtnBack.setOnClickListener {
            startActivity(Intent(this, UserActivity::class.java))
        }

        EdEmail = findViewById(R.id.EdEmail)
        EdPassword = findViewById(R.id.EdPass)
        EdNamaUser = findViewById(R.id.EdNama)
        EdPhone = findViewById(R.id.EdPhone)
        EdAlamat = findViewById(R.id.EdAlamat)
        val role = findViewById<Spinner>(R.id.role)
        BtnSimpan = findViewById(R.id.BtnSimpan)

        val items = resources.getStringArray(R.array.role)
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, items)

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        role.adapter = adapter

        role.setOnItemSelectedListener(object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun onItemSelected(
                parentView: AdapterView<*>,
                selectedItemView: View,
                position: Int,
                id: Long
            ) {
               if (items[position] == "Admin"){
                   Role = "1"
               }else{
                   Role = "2"
               }
            }
        })

            BtnSimpan.setOnClickListener {
            AlertDialog.Builder(this)
                .setTitle("Tambah data?")
                .setPositiveButton("Ya", DialogInterface.OnClickListener { dialog, which ->
                    AddUser()
                })
                .setNegativeButton("Tidak", DialogInterface.OnClickListener { dialog, which ->  })
                .show()
        }
    }

    private fun AddUser() {
        RetrofitClient.instance.AddUser(
            EdNamaUser.text.toString(),
            EdEmail.text.toString(),
            EdPassword.text.toString(),
            EdAlamat.text.toString(),
            Role,
            EdPhone.text.toString()
        ).enqueue(object : Callback<ModelResponse>{
            override fun onFailure(call: Call<ModelResponse>, t: Throwable) {
                Toast.makeText(this@AddUserActivity,"Maaf Sistem sedang gangguan", Toast.LENGTH_LONG).show()
                Log.e("kesalahan API Add : ", t.toString())
            }

            override fun onResponse(call: Call<ModelResponse>, response: Response<ModelResponse>) {
                if (response.isSuccessful){
                    Toast.makeText(this@AddUserActivity, "Berhasil", Toast.LENGTH_LONG).show()
                    startActivity(Intent(this@AddUserActivity, UserActivity::class.java))
                }else{
                    Toast.makeText(this@AddUserActivity, "Kesalahan", Toast.LENGTH_LONG).show()
                }
            }

        })
    }
}
