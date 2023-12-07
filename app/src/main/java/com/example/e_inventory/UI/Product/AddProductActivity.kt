package com.example.e_inventory.UI.Product

import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import androidx.appcompat.app.AlertDialog
import com.example.e_inventory.API.RetrofitClient
import com.example.e_inventory.Model.ModelCategory
import com.example.e_inventory.Model.ModelResponse
import com.example.e_inventory.Model.ModelSupplier
import com.example.e_inventory.R
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AddProductActivity : AppCompatActivity() {

    private lateinit var EdNamaProduct : EditText
    private lateinit var EdStokProduct : EditText
    private lateinit var EdPriceProduct : EditText
    private lateinit var EdDescription : EditText
    private lateinit var EdSupplier : Spinner
    private lateinit var EdCategory : Spinner
    private lateinit var BtnSimpan : Button
    private lateinit var selectedSupplierId: String
    private lateinit var selectedCategoryId: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_product)
        val BtnBack = findViewById<LinearLayout>(R.id.BtnBack)
        BtnBack.setOnClickListener {
            finish()
        }

        EdNamaProduct = findViewById(R.id.EdNamaProduk)
        EdStokProduct = findViewById(R.id.EdStok)
        EdPriceProduct = findViewById(R.id.EdHargaProduk)
        EdDescription = findViewById(R.id.EdDeskProduk)
        EdSupplier = findViewById(R.id.EdSuppProduk)
        EdCategory = findViewById(R.id.EdKategoriProduk)
        BtnSimpan = findViewById(R.id.BtnSimpan)


        fetchSuppliers()
        fetchCategory()

        BtnSimpan.setOnClickListener {
            AlertDialog.Builder(this@AddProductActivity)
                .setTitle("Tambahkan Data?")
                .setPositiveButton("Ya", DialogInterface.OnClickListener { dialog, which ->
                    AddProduct()
                    Toast.makeText(this@AddProductActivity, "Berhasil", Toast.LENGTH_SHORT).show()

                })
                .setNegativeButton("Tidak", DialogInterface.OnClickListener { dialog, which ->  })
                .show()
        }

    }

    private fun AddProduct(){
        RetrofitClient.instance.AddProduct(
            selectedCategoryId,
            selectedSupplierId,
            EdNamaProduct.text.toString(),
            EdStokProduct.text.toString(),
            EdPriceProduct.text.toString(),
            EdDescription.text.toString()
        ).enqueue(object : Callback<ModelResponse>{
            override fun onFailure(call: Call<ModelResponse>, t: Throwable) {
                Toast.makeText(this@AddProductActivity, "Maaf Sistem Sedang Gangguan", Toast.LENGTH_SHORT).show()
                Log.e("Kesalahan API Add Product : ", t.toString())
            }

            override fun onResponse(call: Call<ModelResponse>, response: Response<ModelResponse>) {
                if (response.isSuccessful){
                    Toast.makeText(this@AddProductActivity, "Berhasil", Toast.LENGTH_SHORT).show()
                    startActivity(Intent(this@AddProductActivity, ProductActivity::class.java))
                }else{
                    Toast.makeText(this@AddProductActivity, "Kesalahan", Toast.LENGTH_SHORT).show()
                }
            }

        })
    }

    private fun fetchCategory() {
        RetrofitClient.instance.Category().enqueue(object : Callback<ModelCategory>{
            override fun onFailure(call: Call<ModelCategory>, t: Throwable) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun onResponse(call: Call<ModelCategory>, response: Response<ModelCategory>) {
                if (response.isSuccessful) {
                    val category: List<ModelCategory.dataCategory> = response.body()!!.Category

                    // Isi data supplier ke dalam Spinner
                    setupSpinnerCategory(category)
                }
            }

        })
    }

    private fun setupSpinnerCategory(category: List<ModelCategory.dataCategory>) {
        val supplierNames = category.map { it.name }.toTypedArray()

        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, supplierNames)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        EdCategory.adapter = adapter

        // Menangani item yang dipilih dari spinner
        EdCategory.setOnItemSelectedListener(object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parentView: AdapterView<*>?, selectedItemView: View?, position: Int, id: Long) {
                // Simpan id_supplier dari item yang dipilih
                selectedCategoryId = category[position].category_id
            }

            override fun onNothingSelected(parentView: AdapterView<*>?) {
                // Tidak ada yang dipilih
            }
        })
    }

    private fun fetchSuppliers() {
        RetrofitClient.instance.Supplier().enqueue(object : Callback<ModelSupplier>{
            override fun onFailure(call: Call<ModelSupplier>, t: Throwable) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun onResponse(call: Call<ModelSupplier>, response: Response<ModelSupplier>) {
                if (response.isSuccessful) {
                    val suppliers: List<ModelSupplier.dataSupplier> = response.body()!!.Supplier

                    // Isi data supplier ke dalam Spinner
                    setupSpinner(suppliers)
                }
            }

        })
    }

    private fun setupSpinner(suppliers: List<ModelSupplier.dataSupplier>) {
        val supplierNames = suppliers.map { it.name }.toTypedArray()

        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, supplierNames)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        EdSupplier.adapter = adapter

        // Menangani item yang dipilih dari spinner
        EdSupplier.setOnItemSelectedListener(object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parentView: AdapterView<*>?, selectedItemView: View?, position: Int, id: Long) {
                // Simpan id_supplier dari item yang dipilih
                selectedSupplierId = suppliers[position].id
            }

            override fun onNothingSelected(parentView: AdapterView<*>?) {
                // Tidak ada yang dipilih
            }
        })
    }
}
