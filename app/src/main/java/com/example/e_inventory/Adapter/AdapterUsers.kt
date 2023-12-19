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
import com.example.e_inventory.Model.ModelSupplier
import com.example.e_inventory.Model.ModelUser
import com.example.e_inventory.R

class AdapterUsers(
    val Users : ArrayList<ModelUser.dataUsers>,
    val listener : AdapterUsers.OnAdapterlistener
) :  RecyclerView.Adapter<AdapterUsers.ViewHolder>() {
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val NamaUser = view.findViewById<TextView>(R.id.NamaUser)
        val Phone = view.findViewById<TextView>(R.id.Phone)
        val Email = view.findViewById<TextView>(R.id.Email)
        val Alamat = view.findViewById<TextView>(R.id.Alamat)
        val Gambar = view.findViewById<ImageView>(R.id.GambarUser)
        val BtnEdit = view.findViewById<LinearLayout>(R.id.BtnEdit)
        val BtnHapus = view.findViewById<LinearLayout>(R.id.BtnDelete)


    }
    interface  OnAdapterlistener {
        fun onClick(users: ModelUser.dataUsers)
        fun onDelete(users : ModelUser.dataUsers)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int)= ViewHolder(
        LayoutInflater.from(parent.context)
            .inflate(R.layout.adapter_users, parent, false)
    )

    override fun getItemCount()= Users.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data = Users[position]
        holder.NamaUser.text = data.name
        holder.Phone.text = data.phone_number
        holder.Alamat.text = data.address
        holder.Email.text = data.email


        holder.BtnEdit.setOnClickListener {
            listener.onClick(data)
        }
        holder.BtnHapus.setOnClickListener {
            listener.onDelete(data)
        }
    }
    public fun setData(data: List<ModelUser.dataUsers>){
        Users.clear()
        Users.addAll(data)
        notifyDataSetChanged()
    }

}
