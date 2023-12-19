package com.example.e_inventory.UI.Shipped

import android.app.AlertDialog
import android.app.DatePickerDialog
import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import com.example.e_inventory.API.RetrofitClient
import com.example.e_inventory.Model.ModelProduct
import com.example.e_inventory.Model.ModelResponse
import com.example.e_inventory.R
import com.example.e_inventory.UI.Recieved.RecievedActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.*

class EditShippedActivity : AppCompatActivity() {

    private val id by lazy { intent.getStringExtra("id") }
    private val ProductName by lazy { intent.getStringExtra("product") }
    private val idAwal by lazy { intent.getStringExtra("product_id") }
    private val Qty by lazy { intent.getStringExtra("qty") }
    private val TglKeluar by lazy { intent.getStringExtra("tglKeluar") }
    private val Desk by lazy { intent.getStringExtra("description") }


    private lateinit var EdNamaProduct : Spinner
    private lateinit var EdQty : EditText
    private lateinit var EdTgl : TextView
    private lateinit var EdDescription : EditText
    private lateinit var BtnSimpan : Button
    private lateinit var selectedProductId: String
    private val calendar = Calendar.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_shipped)
        val BtnBack = findViewById<LinearLayout>(R.id.BtnBack)
        BtnBack.setOnClickListener {
            startActivity(Intent(this, ShippeedActivity::class.java))
        }

        EdNamaProduct = findViewById(R.id.EdNamaProduk)
        EdQty = findViewById(R.id.EdQty)
        EdTgl = findViewById(R.id.EdTgl)
        EdDescription = findViewById(R.id.EdDesk)
        BtnSimpan = findViewById(R.id.BtnSimpan)

        fetchProduct()

        EdQty.setText(Qty)
        EdDescription.setText(Desk)

        updateDateInView()
        EdTgl.text = TglKeluar.toString()


        // Menetapkan OnClickListener untuk TextView
        EdTgl.setOnClickListener {
            showDatePickerDialog()
        }

        BtnSimpan.setOnClickListener {
            UpdateShipped()
        }

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
        // Menentukan item yang dipilih berdasarkan data dari halaman sebelumnya
        val selectedCategory = categoryNames.indexOf(ProductName)
        EdNamaProduct.setSelection(selectedCategory)

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

    private fun UpdateShipped(){
        RetrofitClient.instance.EditBarangKeluar(
            id.toString(),
            EdTgl.text.toString(),
            selectedProductId,
            EdQty.text.toString(),
            EdDescription.text.toString(),
            idAwal.toString(),
            Qty.toString()
        ).enqueue(object : Callback<ModelResponse> {
            override fun onFailure(call: Call<ModelResponse>, t: Throwable) {
                Toast.makeText(this@EditShippedActivity, "Maaf Sistem Sedang Gangguan", Toast.LENGTH_SHORT).show()
                Log.e("Kesalahan API Edit Shipped : ", t.toString())
                Log.d("id : ", id.toString())
                Log.d("date : ", TglKeluar.toString())
                Log.d("product_id : ", selectedProductId.toString())
                Log.d("qty : ", EdQty.text.toString())
                Log.d("description : ", EdDescription.text.toString())

            }

            override fun onResponse(call: Call<ModelResponse>, response: Response<ModelResponse>) {
                if (response.isSuccessful){
                    if (response.body()!!.status == "true") {
                        AlertDialog.Builder(this@EditShippedActivity)
                            .setTitle("Apakah Anda Yakin Ingin Mengubah Data?")
                            .setPositiveButton(
                                "Ya",
                                DialogInterface.OnClickListener { dialog, which ->
                                    Toast.makeText(
                                        this@EditShippedActivity,
                                        "Berhasil",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                    Log.d("id : ", id.toString())
                                    Log.d("date : ", EdTgl.text.toString())
                                    Log.d("product_id : ", selectedProductId.toString())
                                    Log.d("qty : ", EdQty.text.toString())
                                    Log.d("description : ", EdDescription.text.toString())
                                })
                            .setNegativeButton(
                                "Tidak",
                                DialogInterface.OnClickListener { dialog, which -> })
                            .show()
                    }else{
                            Toast.makeText(this@EditShippedActivity, response.body()!!.message, Toast.LENGTH_SHORT).show()
                        }
                }else{
                    Toast.makeText(this@EditShippedActivity, "Kesalahan", Toast.LENGTH_SHORT).show()
                }
            }

        })
    }
}
