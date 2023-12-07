package com.example.e_inventory.UI.Beranda

import android.content.DialogInterface
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.e_inventory.API.RetrofitClient
import com.example.e_inventory.Adapter.AdapterCategory
import com.example.e_inventory.Model.ModelCategory
import com.example.e_inventory.R
import com.example.e_inventory.UI.Category.AddCategoryActivity
import com.example.e_inventory.UI.Category.DetailKategoriActivity
import com.example.e_inventory.UI.Login.LoginActivity
import com.example.e_inventory.UI.Product.ProductActivity
import com.example.e_inventory.UI.Profile.ProfileActivity
import com.example.e_inventory.UI.Recieved.RecievedActivity
import com.example.e_inventory.UI.Report.ReportActivity
import com.example.e_inventory.UI.Shipped.ShippeedActivity
import com.example.e_inventory.UI.Supplier.SupplierActivity
import com.example.e_inventory.UI.User.UserActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class BerandaActivity : AppCompatActivity() {

    lateinit var NamaUser : TextView
    lateinit var BtnLogout : ImageView
    lateinit var BtnProduct : CardView
    lateinit var BtnSupplier : CardView
    lateinit var BtnRecived : CardView
    lateinit var BtnShipped : CardView
    lateinit var BtnReport : CardView
    lateinit var BtnUser : CardView
    private lateinit var profil : SharedPreferences
    lateinit var ListCategory : RecyclerView
    lateinit var Adapter : AdapterCategory
    private lateinit var BtnTambah : LinearLayout


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_beranda)
        profil = getSharedPreferences("Login_Session", MODE_PRIVATE)
        NamaUser = findViewById(R.id.txtNama)
        BtnLogout = findViewById(R.id.btnLogout)
        BtnProduct = findViewById(R.id.btnProduct)
        BtnSupplier = findViewById(R.id.btnSupplier)
        BtnRecived = findViewById(R.id.btnRecived)
        BtnShipped = findViewById(R.id.btnShipped)
        BtnReport = findViewById(R.id.btnReport)
        BtnUser = findViewById(R.id.btnUser)
        BtnTambah = findViewById(R.id.btnAdd)


        getCategory()
        setUpListCategory()

        NamaUser.text = "Hey, ${profil.getString("name", null).toString()}"

        BtnLogout.setOnClickListener {
            AlertDialog.Builder(this)
                .setTitle("Apakah Anda Yakin Ingin Keluar?")
                .setPositiveButton("Ya", DialogInterface.OnClickListener { dialog, which ->
                    //menghapus session
                    profil.edit().clear().commit()
                    startActivity(Intent(this, LoginActivity::class.java))
                })
                .setNegativeButton("Tidak", DialogInterface.OnClickListener { dialog, which ->  })
                .show()
        }
        BtnUser.setOnClickListener {
            startActivity(Intent(this, UserActivity::class.java))
        }
        BtnProduct.setOnClickListener {
            startActivity(Intent(this, ProductActivity::class.java))
        }
        BtnSupplier.setOnClickListener {
            startActivity(Intent(this, SupplierActivity::class.java))
        }
        BtnRecived.setOnClickListener {
            startActivity(Intent(this, RecievedActivity::class.java))
        }
        BtnShipped.setOnClickListener {
            startActivity(Intent(this, ShippeedActivity::class.java))
        }
        BtnReport.setOnClickListener {
            startActivity(Intent(this, ReportActivity::class.java))
        }
        NamaUser.setOnClickListener {
            getSharedPreferences("Login_Session", MODE_PRIVATE)
                .edit()
                .putString("id", profil.getString("id", null))
                .putString("name", profil.getString("name", null))
                .putString("email", profil.getString("email", null))
                .putString("password", profil.getString("password", null))
                .putString("address", profil.getString("address", null))
                .putString("phone", profil.getString("phone", null))
                .putString("role", profil.getString("role", null))
                .apply()
            startActivity(Intent(this, ProfileActivity::class.java))
        }
        BtnTambah.setOnClickListener {
            startActivity(Intent(this, AddCategoryActivity::class.java))
        }
    }

    private fun getCategory() {
        RetrofitClient.instance.Category().enqueue(object : Callback<ModelCategory>{
            override fun onFailure(call: Call<ModelCategory>, t: Throwable) {
                Toast.makeText(this@BerandaActivity, "Maaf Sistem Sedang Gangguan", Toast.LENGTH_SHORT).show()
                Log.e("Kesalahan API Kategori : ", t.toString())
            }

            override fun onResponse(
                call: Call<ModelCategory>,
                response: Response<ModelCategory>
            ) {
                if (response.isSuccessful){
                    val ListData = response.body()!!.Category
                    ListData.forEach {
                        Adapter.setData(ListData)
                    }
                } else{
                    Toast.makeText(this@BerandaActivity, "Maaf Terjadi Kesalahan", Toast.LENGTH_SHORT).show()
                }
            }

        })

    }
    private fun setUpListCategory() {
        ListCategory = findViewById(R.id.ListCategory)
        Adapter = AdapterCategory(arrayListOf(), object : AdapterCategory.OnAdapterlistener{
            override fun onClick(category: ModelCategory.dataCategory) {
                startActivity(Intent(this@BerandaActivity, DetailKategoriActivity::class.java)
                    .putExtra("categoryId", category.category_id)
                    .putExtra("namaKategori", category.name)
                    .putExtra("statusCategory", category.status))
            }

        })
        ListCategory.adapter = Adapter
    }
}
