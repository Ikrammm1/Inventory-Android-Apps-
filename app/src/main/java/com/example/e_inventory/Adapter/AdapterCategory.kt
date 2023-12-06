package com.example.e_inventory.Adapter

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.e_inventory.Model.ModelCategory
import com.example.e_inventory.R

class AdapterCategory(
    val Category : ArrayList<ModelCategory.dataCategory>,
    val listener : OnAdapterlistener
): RecyclerView.Adapter<AdapterCategory.ViewHolder>(){
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val Nama = view.findViewById<TextView>(R.id.NamaKategori)
        val Icon = view.findViewById<ImageView>(R.id.GambarKategori)

    }
    interface  OnAdapterlistener {
        fun onClick(category: ModelCategory.dataCategory)

    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int)= ViewHolder (
        LayoutInflater.from(parent.context)
            .inflate(R.layout.adapter_category,parent,false)
        )


    override fun getItemCount()=Category.size

    override fun onBindViewHolder(holder: AdapterCategory.ViewHolder, position: Int) {
        val data = Category[position]
        holder.Nama.text = data.name
        holder.itemView.setOnClickListener {
            listener.onClick(data)
        }

    }
    public fun setData(data: List<ModelCategory.dataCategory>){
        Category.clear()
        Category.addAll(data)
        notifyDataSetChanged()
    }

}
