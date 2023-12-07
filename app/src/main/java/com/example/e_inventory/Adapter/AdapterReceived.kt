package com.example.e_inventory.Adapter

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.e_inventory.Model.ModelProduct
import com.example.e_inventory.Model.ModelRecieved
import com.example.e_inventory.R

class AdapterReceived(
    val Received : ArrayList<ModelRecieved.dataRecieved>,
    val listener : AdapterReceived.OnAdapterlistener
) :  RecyclerView.Adapter<AdapterReceived.ViewHolder>(){

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val NamaProduk = view.findViewById<TextView>(R.id.NamaProduk)
        val Qty = view.findViewById<TextView>(R.id.Quantity)
        val DateIn = view.findViewById<TextView>(R.id.TglMasuk)
        val Kategori = view.findViewById<TextView>(R.id.Kategori)
        val Supplier = view.findViewById<TextView>(R.id.Supplier)
        val Phone = view.findViewById<TextView>(R.id.Phone)
        val Alamat = view.findViewById<TextView>(R.id.Alamat)
        val BtnEdit = view.findViewById<TextView>(R.id.BtnEdit)
        val BtnHapus = view.findViewById<TextView>(R.id.BtnDelete)

    }
    interface  OnAdapterlistener {
        fun onClick(received: ModelRecieved.dataRecieved)
        fun onDelete(received: ModelRecieved.dataRecieved)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int)= ViewHolder (
        LayoutInflater.from(parent.context)
            .inflate(R.layout.adapter_received, parent, false)
    )

    override fun getItemCount()=Received.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data = Received[position]
        holder.NamaProduk.text = data.product
        holder.DateIn.text = data.date_in
        holder.Qty.text = data.qty
        holder.Kategori.text = data.category
        holder.Supplier.text = data.supplier
        holder.Phone.text = data.supplier_phone
        holder.Alamat.text = data.supplier_address

        holder.BtnEdit.setOnClickListener {
            listener.onClick(data)
        }
        holder.BtnHapus.setOnClickListener {
            listener.onDelete(data)
        }
    }

    public fun setData(data: List<ModelRecieved.dataRecieved>){
        Received.clear()
        Received.addAll(data)
        notifyDataSetChanged()
    }

}
