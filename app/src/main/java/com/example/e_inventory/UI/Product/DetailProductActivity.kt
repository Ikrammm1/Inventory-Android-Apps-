package com.example.e_inventory.UI.Product

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import com.example.e_inventory.API.RetrofitClient
import com.example.e_inventory.Model.ModelCategory
import com.example.e_inventory.Model.ModelResponse
import com.example.e_inventory.Model.ModelSupplier
import com.example.e_inventory.R
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailProductActivity : AppCompatActivity() {

    private val id by lazy { intent.getStringExtra("id") }
    private val NamaProduct by lazy { intent.getStringExtra("name") }
    private val Price by lazy { intent.getStringExtra("price") }
    private val Stok by lazy { intent.getStringExtra("stok") }
    private val Description by lazy { intent.getStringExtra("description") }
    private val CategoryId by lazy { intent.getStringExtra("category_id") }
    private val Category by lazy { intent.getStringExtra("category") }
    private val SupplierId by lazy { intent.getStringExtra("supplier_id") }
    private val Supplier by lazy { intent.getStringExtra("supplier") }
    private val SupplierAddress by lazy { intent.getStringExtra("supplier_address") }
    private val SupplierPhone by lazy { intent.getStringExtra("supplier_phone") }
    private val Status by lazy { intent.getStringExtra("status") }

    private lateinit var EdNamaProduct : EditText
    private lateinit var EdStokProduct : EditText
    private lateinit var EdPriceProduct : EditText
    private lateinit var EdDescription : EditText
    private lateinit var EdSupplier : Spinner
    private lateinit var EdCategory : Spinner
    private lateinit var EdStatus : Spinner
    private lateinit var BtnSimpan : Button
    private lateinit var selectedSupplierId: String
    private lateinit var selectedCategoryId: String
    private lateinit var NewStatus : String


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_product)
        val BtnBack = findViewById<LinearLayout>(R.id.BtnBack)
        BtnBack.setOnClickListener {
            startActivity(Intent(this, ProductActivity::class.java))
        }

        EdNamaProduct = findViewById(R.id.EdNamaProduk)
        EdStokProduct = findViewById(R.id.EdStok)
        EdPriceProduct = findViewById(R.id.EdHargaProduk)
        EdDescription = findViewById(R.id.EdDeskProduk)
        EdSupplier = findViewById(R.id.EdSuppProduk)
        EdCategory = findViewById(R.id.EdKategoriProduk)
        EdStatus = findViewById(R.id.EdStatus)
        BtnSimpan = findViewById(R.id.BtnSimpan)

        EdNamaProduct.setText(NamaProduct)
        EdStokProduct.setText(Stok)
        EdPriceProduct.setText(Price)
        EdDescription.setText(Description)

        fetchSuppliers()
        fetchCategory()

        val items = resources.getStringArray(R.array.status)

        val selectedItemPosition = when (Status) {
            "aktif" -> items.indexOf("aktif")
            "nonaktif" -> items.indexOf("nonaktif")
            else -> 0 // Pilih item pertama jika tidak ada data yang sesuai
        }

        // Set item yang dipilih pada spinner
        EdStatus.setSelection(selectedItemPosition)

        EdStatus.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parentView: AdapterView<*>?, selectedItemView: View?, position: Int, id: Long) {
                NewStatus = items[position]
                // Lakukan sesuatu dengan item yang dipilih
            }

            override fun onNothingSelected(parentView: AdapterView<*>?) {
                // Tidak ada yang dipilih
            }
        }

        BtnSimpan.setOnClickListener {
            UpdateProduct()
        }


    }
    private fun fetchCategory() {
        RetrofitClient.instance.Category().enqueue(object : Callback<ModelCategory> {
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
        val categoryNames = category.map { it.name }.toTypedArray()

        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, categoryNames)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        EdCategory.adapter = adapter
        // Menentukan item yang dipilih berdasarkan data dari halaman sebelumnya
        val selectedCategory = categoryNames.indexOf(Category)
        EdCategory.setSelection(selectedCategory)

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
        RetrofitClient.instance.Supplier().enqueue(object : Callback<ModelSupplier> {
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

        // Menentukan item yang dipilih berdasarkan data dari halaman sebelumnya
        val selectedSupplier = supplierNames.indexOf(Supplier)
        EdSupplier.setSelection(selectedSupplier)

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

    private fun UpdateProduct(){
        RetrofitClient.instance.EditProduct(
            id.toString(),
            selectedCategoryId,
            selectedSupplierId,
            EdNamaProduct.text.toString(),
            EdStokProduct.text.toString(),
            EdPriceProduct.text.toString(),
            EdDescription.text.toString(),
            NewStatus
        ).enqueue(object : Callback<ModelResponse>{
            override fun onFailure(call: Call<ModelResponse>, t: Throwable) {
                Toast.makeText(this@DetailProductActivity, "Maaf Sistem Sedang Gangguan", Toast.LENGTH_SHORT).show()
                Log.e("Kesalahan API Update Product : ", t.toString())
            }

            override fun onResponse(call: Call<ModelResponse>, response: Response<ModelResponse>) {
                if (response.isSuccessful){
                    Toast.makeText(this@DetailProductActivity, "Berhasil", Toast.LENGTH_SHORT).show()
                }else{
                    Toast.makeText(this@DetailProductActivity, "Kesalahan", Toast.LENGTH_SHORT).show()
                }
            }

        })
    }

}
