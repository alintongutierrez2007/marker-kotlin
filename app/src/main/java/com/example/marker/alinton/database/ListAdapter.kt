package com.example.marker.alinton.database

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.example.marker.alinton.R
import com.example.marker.alinton.UpdateActivity
import com.example.marker.alinton.ViewMarkerActivity
import kotlinx.android.synthetic.main.custom_row.view.*

class ListAdapter(): RecyclerView.Adapter<ListAdapter.MyViewHolder>() {

    private var markerList= emptyList<Marker>()

    private var markerListFiltered = emptyList<Marker>()


    class MyViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){}



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.custom_row, parent,false))
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {



        val currentItem= markerList[position]
        holder.itemView.Name_txt.text=currentItem.nombre
      //holder.itemView.Name_txt.text=currentItem.nombre.toString()
        holder.itemView.Descripcion_txt.text=currentItem.descripcion.toString()
        holder.itemView.Fecha_txt.text= currentItem.fecha_hora

        holder.itemView.button_editar.setOnClickListener{v->
           println("aqui vamos a aditar datos")
            val intent= Intent(v.context,UpdateActivity::class.java)
            intent.putExtra("id",currentItem.id)
            intent.putExtra("nombre",currentItem.nombre)
            intent.putExtra("descripcion",currentItem.descripcion)

            intent.putExtra("fecha_hora",currentItem.fecha_hora)
            intent.putExtra("latitude",currentItem.latitude)
            intent.putExtra("longitude",currentItem.longitude)

            v.context.startActivity(intent)
        }


        holder.itemView.button_ver.setOnClickListener{ v->
            val intent= Intent(v.context,ViewMarkerActivity::class.java)

            intent.putExtra("nombre",currentItem.nombre)
            intent.putExtra("descripcion",currentItem.descripcion)

            intent.putExtra("fecha_hora",currentItem.fecha_hora)
            intent.putExtra("latitude",currentItem.latitude)
            intent.putExtra("longitude",currentItem.longitude)

            v.context.startActivity(intent)
        }


    }

    override fun getItemCount(): Int {
        return markerList. size
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setData(marker: List<Marker>){
        this.markerList=marker
        notifyDataSetChanged()
    }


}