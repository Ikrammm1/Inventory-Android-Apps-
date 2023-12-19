package com.example.e_inventory.Adapter

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.e_inventory.Model.ModelRecieved
import com.example.e_inventory.Model.ModelShipped
import com.example.e_inventory.R

class AdapterShipped(
    val Shipped : ArrayList<ModelShipped.dataShipped>,
    val listener : AdapterShipped.OnAdapterlistener
) :  RecyclerView.Adapter<AdapterShipped.ViewHolder>(){

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val NamaProduk = view.findViewById<TextView>(R.id.NamaProduk)
        val Qty = view.findViewById<TextView>(R.id.Quantity)
        val DateOut = view.findViewById<TextView>(R.id.TglKeluar)
        val Kategori = view.findViewById<TextView>(R.id.Kategori)
        val BtnEdit = view.findViewById<TextView>(R.id.BtnEdit)
        val BtnHapus = view.findViewById<TextView>(R.id.BtnDelete)

    }
    interface  OnAdapterlistener {
        fun onClick(shipped: ModelShipped.dataShipped)
        fun onDelete(shipped: ModelShipped.dataShipped)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int)= ViewHolder (
        LayoutInflater.from(parent.context)
            .inflate(R.layout.adapter_shipped, parent, false)
    )

    override fun getItemCount()=Shipped.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data = Shipped[position]
        holder.NamaProduk.text = data.product
        holder.DateOut.text = data.date_out
        holder.Qty.text = "Quantity : ${data.qty}"
        holder.Kategori.text = data.category

        holder.BtnEdit.setOnClickListener {
            listener.onClick(data)
        }
        holder.BtnHapus.setOnClickListener {
            listener.onDelete(data)
        }
    }

    public fun setData(data: List<ModelShipped.dataShipped>){
        Shipped.clear()
        Shipped.addAll(data)
        notifyDataSetChanged()
    }

}