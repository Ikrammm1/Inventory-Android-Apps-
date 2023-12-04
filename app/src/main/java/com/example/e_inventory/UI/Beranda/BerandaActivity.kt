package com.example.e_inventory.UI.Beranda

import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.cardview.widget.CardView
import com.example.e_inventory.R
import com.example.e_inventory.UI.Login.LoginActivity
import com.example.e_inventory.UI.Product.ProductActivity
import com.example.e_inventory.UI.Profile.ProfileActivity
import com.example.e_inventory.UI.Recieved.RecievedActivity
import com.example.e_inventory.UI.Report.ReportActivity
import com.example.e_inventory.UI.Shipped.ShippeedActivity
import com.example.e_inventory.UI.Supplier.SupplierActivity
import com.example.e_inventory.UI.User.UserActivity

class BerandaActivity : AppCompatActivity() {

    lateinit var NamaUser : TextView
    lateinit var BtnLogout : ImageView
    lateinit var BtnProduct : CardView
    lateinit var BtnSupplier : CardView
    lateinit var BtnRecived : CardView
    lateinit var BtnShipped : CardView
    lateinit var BtnReport : CardView
    lateinit var BtnUser : CardView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_beranda)
        NamaUser = findViewById(R.id.txtNama)
        BtnLogout = findViewById(R.id.btnLogout)
        BtnProduct = findViewById(R.id.btnProduct)
        BtnSupplier = findViewById(R.id.btnSupplier)
        BtnRecived = findViewById(R.id.btnRecived)
        BtnShipped = findViewById(R.id.btnShipped)
        BtnReport = findViewById(R.id.btnReport)
        BtnUser = findViewById(R.id.btnUser)

        BtnLogout.setOnClickListener {
            AlertDialog.Builder(this)
                .setTitle("Apakah Anda Yakin Ingin Keluar?")
                .setPositiveButton("Ya", DialogInterface.OnClickListener { dialog, which ->
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
            startActivity(Intent(this, ProfileActivity::class.java))
        }
    }
}
