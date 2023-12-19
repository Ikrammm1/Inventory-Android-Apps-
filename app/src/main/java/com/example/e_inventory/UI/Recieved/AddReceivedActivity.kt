package com.example.e_inventory.UI.Recieved

import android.app.AlertDialog
import android.app.DatePickerDialog
import android.content.DialogInterface
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import com.example.e_inventory.API.RetrofitClient
import com.example.e_inventory.Model.ModelProduct
import com.example.e_inventory.Model.ModelResponse
import com.example.e_inventory.R
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.*

class AddReceivedActivity : AppCompatActivity() {

    private lateinit var EdNamaProduct : Spinner
    private lateinit var EdQty : EditText
    private lateinit var EdTgl : TextView
    private lateinit var EdDescription : EditText
    private lateinit var BtnSimpan : Button
    private lateinit var selectedProductId: String
    private val calendar = Calendar.getInstance()
    private lateinit var profil : SharedPreferences


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_received)

        val BtnBack = findViewById<LinearLayout>(R.id.BtnBack)
        BtnBack.setOnClickListener {
            startActivity(Intent(this, RecievedActivity::class.java))
        }
        EdNamaProduct = findViewById(R.id.EdNamaProduk)
        EdQty = findViewById(R.id.Edqty)
        EdTgl = findViewById(R.id.EdTgl)
        EdDescription = findViewById(R.id.EdDesk)
        BtnSimpan = findViewById(R.id.BtnSimpan)
        profil = getSharedPreferences("Login_Session", MODE_PRIVATE)

        fetchProduct()
        updateDateInView()

        // Menetapkan OnClickListener untuk TextView
        EdTgl.setOnClickListener {
            showDatePickerDialog()
        }

        BtnSimpan.setOnClickListener {
            AddReceived()
        }
    }

    private fun AddReceived() {
        RetrofitClient.instance.AddBarangMasuk(
            EdTgl.text.toString(),
            profil.getString("id",null).toString(),
            selectedProductId,
            EdQty.text.toString(),
            EdDescription.text.toString()
        ).enqueue(object :Callback<ModelResponse>{
            override fun onFailure(call: Call<ModelResponse>, t: Throwable) {
                Toast.makeText(this@AddReceivedActivity, "Maaf Sistem Sedang Gangguan", Toast.LENGTH_SHORT).show()
                Log.e("Kesalahan API Edit Received : ", t.toString())
                Log.d("date : ", EdTgl.text.toString())
                Log.d("admin : ", profil.getString("id",null).toString())
                Log.d("product_id : ", selectedProductId.toString())
                Log.d("qty : ", EdQty.text.toString())
                Log.d("description : ", EdDescription.text.toString())
            }

            override fun onResponse(call: Call<ModelResponse>, response: Response<ModelResponse>) {
                if (response.isSuccessful){
                    AlertDialog.Builder(this@AddReceivedActivity)
                        .setTitle("Tambah Data?")
                        .setPositiveButton("Ya", DialogInterface.OnClickListener { dialog, which ->
                            Toast.makeText(this@AddReceivedActivity, "Berhasil", Toast.LENGTH_SHORT).show()
                            startActivity(Intent(this@AddReceivedActivity, RecievedActivity::class.java))
                        })
                        .setNegativeButton("Tidak", DialogInterface.OnClickListener { dialog, which ->  })
                        .show()
                }else{
                    Toast.makeText(this@AddReceivedActivity, "Kesalahan", Toast.LENGTH_SHORT).show()
                }
            }

        })
    }

    private fun showDatePickerDialog() {
        val datePickerDialog = DatePickerDialog(
            this,
            DatePickerDialog.OnDateSetListener { _, year, monthOfYear, dayOfMonth ->
                calendar.set(Calendar.YEAR, year)
                calendar.set(Calendar.MONTH, monthOfYear)
                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)
                updateDateInView()
            },
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        )

        // Tampilkan DatePickerDialog
        datePickerDialog.show()
    }

    private fun updateDateInView() {
        val myFormat = "yyyy-MM-dd" // Format tanggal yang diinginkan
        val sdf = SimpleDateFormat(myFormat, Locale.getDefault())
        EdTgl.text = sdf.format(calendar.time)
    }

    private fun fetchProduct() {
        RetrofitClient.instance.Product().enqueue(object : Callback<ModelProduct> {
            override fun onFailure(call: Call<ModelProduct>, t: Throwable) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun onResponse(call: Call<ModelProduct>, response: Response<ModelProduct>) {
                if (response.isSuccessful) {
                    val product: List<ModelProduct.dataProduct> = response.body()!!.Product

                    // Isi data supplier ke dalam Spinner
                    setupSpinnerProduct(product)
                }
            }

        })
    }

    private fun setupSpinnerProduct(product: List<ModelProduct.dataProduct>) {
        val categoryNames = product.map { it.name }.toTypedArray()

        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, categoryNames)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        EdNamaProduct.adapter = adapter

        // Menangani item yang dipilih dari spinner
        EdNamaProduct.setOnItemSelectedListener(object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parentView: AdapterView<*>?, selectedItemView: View?, position: Int, id: Long) {
                // Simpan id_supplier dari item yang dipilih
                selectedProductId = product[position].id
            }

            override fun onNothingSelected(parentView: AdapterView<*>?) {
                // Tidak ada yang dipilih
            }
        })
    }
}
