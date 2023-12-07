package com.example.e_inventory.Adapter

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
import com.example.e_inventory.Model.ModelSupplier
import com.example.e_inventory.R

class AdapterSupplier (
    val Supplier : ArrayList<ModelSupplier.dataSupplier>,
    val listener : AdapterSupplier.OnAdapterlistener
) :  RecyclerView.Adapter<AdapterSupplier.ViewHolder>() {
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val NamaSupplier = view.findViewById<TextView>(R.id.NamaSupplier)
        val PhoneSupplier = view.findViewById<TextView>(R.id.PhoneSupplier)
        val Alamat = view.findViewById<TextView>(R.id.Alamat)
        val Gambar = view.findViewById<ImageView>(R.id.GambarSupplier)
        val BtnEdit = view.findViewById<LinearLayout>(R.id.BtnEdit)
        val BtnHapus = view.findViewById<LinearLayout>(R.id.BtnDelete)


    }
    interface  OnAdapterlistener {
        fun onClick(supplier: ModelSupplier.dataSupplier)
        fun onDelete(supplier : ModelSupplier.dataSupplier)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int)= ViewHolder(
        LayoutInflater.from(parent.context)
            .inflate(R.layout.adapter_supplier, parent, false)
    )

    override fun getItemCount()= Supplier.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data = Supplier[position]
        holder.NamaSupplier.text = data.name
        holder.PhoneSupplier.text = data.phone_number
        holder.Alamat.text = data.address

        holder.BtnEdit.setOnClickListener {
            listener.onClick(data)
        }
        holder.BtnHapus.setOnClickListener {
            listener.onDelete(data)
        }
    }
    public fun setData(data: List<ModelSupplier.dataSupplier>){
        Supplier.clear()
        Supplier.addAll(data)
        notifyDataSetChanged()
    }

}
