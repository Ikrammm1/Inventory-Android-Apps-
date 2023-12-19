package com.example.e_inventory.UI.Report

import android.app.DatePickerDialog
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import com.example.e_inventory.R
import com.example.e_inventory.UI.Recieved.RecievedActivity
import java.net.URLEncoder
import java.text.SimpleDateFormat
import java.util.*

class ExportActivity : AppCompatActivity() {

    private lateinit var TglAwal : EditText
    private lateinit var TglAkhir : EditText
    private lateinit var BtnExport : Button
    private lateinit var Awal : String
    private lateinit var Akhir : String
    private lateinit var Url : String
    private lateinit var uri : Uri
    lateinit var pindah : Intent
    private val calendar = Calendar.getInstance()
    private val calendar2 = Calendar.getInstance()


    private val export by lazy { intent.getStringExtra("export") }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_export)

        val BtnBack = findViewById<LinearLayout>(R.id.BtnBack)
        BtnBack.setOnClickListener {
            startActivity(Intent(this, ReportActivity::class.java))
        }

        TglAwal = findViewById(R.id.EdTglStart)
        TglAkhir = findViewById(R.id.EdTglEnd)
        BtnExport = findViewById(R.id.BtnExport)

        updateDateInViewAwal()
        updateDateInViewAkhir()

        TglAwal.setOnClickListener {
            showDatePickerDialogAwal()
        }
        TglAkhir.setOnClickListener {
            showDatePickerDialogAkhir()
        }
        BtnExport.setOnClickListener {
            // Memulai aktivitas untuk membuka URL
            startActivity(pindah)
            Log.d("tglAwal", Awal)
            Log.d("tglAkhir", Akhir)
            Log.d("url", uri.toString())

        }


    }
    private fun showDatePickerDialogAwal() {
        val datePickerDialog = DatePickerDialog(
            this,
            DatePickerDialog.OnDateSetListener { _, year, monthOfYear, dayOfMonth ->
                calendar.set(Calendar.YEAR, year)
                calendar.set(Calendar.MONTH, monthOfYear)
                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)
                updateDateInViewAwal()
            },
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        )

        // Tampilkan DatePickerDialog
        datePickerDialog.show()
    }

    private fun updateDateInViewAwal() {
        val myFormat = "yyyy-MM-dd" // Format tanggal yang diinginkan
        val sdf = SimpleDateFormat(myFormat, Locale.getDefault())
        TglAwal.setText(sdf.format(calendar.time))
        Awal = sdf.format(calendar.time)
    }

    private fun showDatePickerDialogAkhir() {
        val datePickerDialog = DatePickerDialog(
            this,
            DatePickerDialog.OnDateSetListener { _, year, monthOfYear, dayOfMonth ->
                calendar2.set(Calendar.YEAR, year)
                calendar2.set(Calendar.MONTH, monthOfYear)
                calendar2.set(Calendar.DAY_OF_MONTH, dayOfMonth)
                updateDateInViewAkhir()
            },
            calendar2.get(Calendar.YEAR),
            calendar2.get(Calendar.MONTH),
            calendar2.get(Calendar.DAY_OF_MONTH)
        )

        // Tampilkan DatePickerDialog
        datePickerDialog.show()
    }

    private fun updateDateInViewAkhir() {
        val myFormat = "yyyy-MM-dd" // Format tanggal yang diinginkan
        val sdf = SimpleDateFormat(myFormat, Locale.getDefault())
        TglAkhir.setText(sdf.format(calendar2.time))
        Akhir = sdf.format(calendar2.time)
        URLEncoder.encode(Awal, "UTF-8")
        if (export.toString() == "Received"){

            Url = "https://condign-shells.000webhostapp.com/Inventory/API-Inventory/ReportReceived.php?dateStart=${TglAwal.text.toString()}&dateEnd=${TglAkhir.text.toString()}"

        }else{
            Url = "https://condign-shells.000webhostapp.com/Inventory/API-Inventory/ReportShipped.php?dateStart=${TglAwal.text.toString()}&dateEnd=${TglAkhir.text.toString()}"
        }
        uri = Uri.parse(Url)
        pindah = Intent(Intent.ACTION_VIEW, uri)
    }
}
