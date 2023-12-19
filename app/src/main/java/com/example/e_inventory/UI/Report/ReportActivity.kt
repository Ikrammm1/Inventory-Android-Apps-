package com.example.e_inventory.UI.Report

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.LinearLayout
import androidx.cardview.widget.CardView

import com.example.e_inventory.R
import com.example.e_inventory.UI.Beranda.BerandaActivity
import com.example.e_inventory.UI.Recieved.RecievedActivity


class ReportActivity : AppCompatActivity() {
    private lateinit var BtnReceived : CardView
    private lateinit var BtnShipped : CardView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_report)

        BtnReceived = findViewById(R.id.btnReceived)
        BtnShipped = findViewById(R.id.btnShipped)

        val BtnBack = findViewById<LinearLayout>(R.id.BtnBack)
        BtnBack.setOnClickListener {
            startActivity(Intent(this, BerandaActivity::class.java))
        }

        BtnReceived.setOnClickListener {
            startActivity(Intent(this, ExportActivity::class.java)
                .putExtra("export", "Received"))
        }

        BtnShipped.setOnClickListener {
            startActivity(Intent(this, ExportActivity::class.java)
                .putExtra("export", "Shipped"))
        }


    }
}
