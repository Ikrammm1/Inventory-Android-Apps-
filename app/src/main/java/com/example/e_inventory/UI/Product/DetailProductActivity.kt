package com.example.e_inventory.UI.Product

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import com.example.e_inventory.R

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
    private lateinit var EdSupplier : EditText
    private lateinit var EdCategory : EditText
    private lateinit var EdStatus : EditText
    private lateinit var BtnSimpan : Button


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_product)

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
        EdSupplier.setText(Supplier)
        EdCategory.setText(Category)
        EdStatus.setText(Status)


    }
}
