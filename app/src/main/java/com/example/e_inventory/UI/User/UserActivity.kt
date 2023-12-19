package com.example.e_inventory.UI.User

import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.e_inventory.API.RetrofitClient
import com.example.e_inventory.Adapter.AdapterSupplier
import com.example.e_inventory.Adapter.AdapterUsers
import com.example.e_inventory.Model.ModelResponse
import com.example.e_inventory.Model.ModelSupplier
import com.example.e_inventory.Model.ModelUser
import com.example.e_inventory.R
import com.example.e_inventory.UI.Beranda.BerandaActivity
import com.example.e_inventory.UI.Supplier.AddSupplierActivity
import com.example.e_inventory.UI.Supplier.DetailSupplierActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UserActivity : AppCompatActivity() {

    lateinit var ListUser : RecyclerView
    lateinit var Adapter : AdapterUsers
    lateinit var BtnAdd : ImageView
    private lateinit var swipeRefreshLayout: SwipeRefreshLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user)
        val BtnBack = findViewById<LinearLayout>(R.id.BtnBack)
        getUsers()
        setUpListUser()

        swipeRefreshLayout = findViewById(R.id.swipeRefreshLayout)
        swipeRefreshLayout.setOnRefreshListener {
            getUsers()
            setUpListUser()
            swipeRefreshLayout.isRefreshing = false
        }

        BtnBack.setOnClickListener {
            startActivity(Intent(this, BerandaActivity::class.java))
        }
        BtnAdd = findViewById(R.id.btnAdd)

        BtnAdd.setOnClickListener {
            startActivity(Intent(this, AddUserActivity::class.java))
        }
    }
    private fun getUsers(){
        RetrofitClient.instance.Users().enqueue(object : Callback<ModelUser> {
            override fun onFailure(call: Call<ModelUser>, t: Throwable) {
                Toast.makeText(this@UserActivity, "Maaf Sistem Sedang Gangguan", Toast.LENGTH_SHORT).show()
                Log.e("Kesalahan API Supplier : ", t.toString())
            }

            override fun onResponse(call: Call<ModelUser>, response: Response<ModelUser>) {
                if (response.isSuccessful){
                    val ListData = response.body()!!.Users
                    ListData.forEach {
                        Adapter.setData(ListData)
                    }
                } else{
                    Toast.makeText(this@UserActivity, "Maaf Terjadi Kesalahan", Toast.LENGTH_SHORT).show()
                }
            }

        })
    }

    private fun setUpListUser(){
        ListUser = findViewById(R.id.ListUser)
        Adapter = AdapterUsers(arrayListOf(), object : AdapterUsers.OnAdapterlistener{
            override fun onClick(users: ModelUser.dataUsers) {
                startActivity(
                    Intent(this@UserActivity, UpdateUserActivity::class.java)
                        .putExtra("id", users.id)
                        .putExtra("name", users.name)
                        .putExtra("phone", users.phone_number)
                        .putExtra("alamat", users.address)
                        .putExtra("email", users.email)
                        .putExtra("role", users.role)

                )
            }

            override fun onDelete(users: ModelUser.dataUsers) {
                RetrofitClient.instance.DeleteUser(users.id)
                    .enqueue(object : Callback<ModelResponse> {
                        override fun onFailure(call: Call<ModelResponse>, t: Throwable) {
                            Toast.makeText(this@UserActivity, "Maaf Sistem Sedang Gangguan", Toast.LENGTH_SHORT).show()
                            Log.e("Kesalahan API Delete : ", t.toString())
                        }

                        override fun onResponse(
                            call: Call<ModelResponse>,
                            response: Response<ModelResponse>
                        ) {
                            if (response.isSuccessful){
                                AlertDialog.Builder(this@UserActivity)
                                    .setTitle("Apakah Anda Yakin Ingin Hapus Data?")
                                    .setPositiveButton("Ya", DialogInterface.OnClickListener { dialog, which ->
                                        Toast.makeText(this@UserActivity, "Berhasil", Toast.LENGTH_SHORT).show()
                                        getUsers()
                                    })
                                    .setNegativeButton("Tidak", DialogInterface.OnClickListener { dialog, which ->  })
                                    .show()

                            }else{
                                Toast.makeText(this@UserActivity, "Kesalahan", Toast.LENGTH_SHORT).show()
                            }
                        }

                    })
            }

        })
        ListUser.adapter = Adapter

    }
}
