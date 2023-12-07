package com.example.e_inventory.Adapter

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.e_inventory.Model.ModelProduct
import com.example.e_inventory.R

class AdapterProduct(
    val Product : ArrayList<ModelProduct.dataProduct>,
    val listener : AdapterProduct.OnAdapterlistener
) :  RecyclerView.Adapter<AdapterProduct.ViewHolder>(){
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val NamaProduk = view.findViewById<TextView>(R.id.NamaProduk)
        val HargaProduk = view.findViewById<TextView>(R.id.HargaProduk)
        val StokProduk = view.findViewById<TextView>(R.id.Stok)

    }
    interface  OnAdapterlistener {
        fun onClick(product: ModelProduct.dataProduct)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int)=ViewHolder(
        LayoutInflater.from(parent.context)
            .inflate(R.layout.adapter_product, parent, false)
    )

    override fun getItemCount()= Product.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data = Product[position]
        holder.NamaProduk.text = data.name
        holder.HargaProduk.text = "Rp. ${data.price.toString()}"
        holder.StokProduk.text = "Stok :${data.stok.toString()}"

        holder.itemView.setOnClickListener {
            listener.onClick(data)
        }
    }

    public fun setData(data: List<ModelProduct.dataProduct>){
        Product.clear()
        Product.addAll(data)
        notifyDataSetChanged()
    }
}
