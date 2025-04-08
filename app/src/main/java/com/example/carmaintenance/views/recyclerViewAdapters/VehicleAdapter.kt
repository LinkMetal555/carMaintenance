package com.example.carmaintenance.views.recyclerViewAdapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.carmaintenance.R
import com.example.carmaintenance.model.Vehicle

class VehicleAdapter: RecyclerView.Adapter<VehicleAdapter.ViewHolder>() {

    private var list = listOf<Vehicle>()

    class ViewHolder(view: View): RecyclerView.ViewHolder(view) {
        val carNameText: TextView = view.findViewById(R.id.carNameText)
        val modelText: TextView = view.findViewById(R.id.modelText)
        val plateText: TextView = view.findViewById(R.id.plateText)
        val kmText: TextView = view.findViewById(R.id.kmText)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.vehicle_item_layout, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val vehicle: Vehicle = list[position]
        holder.carNameText.text = vehicle.name
        holder.modelText.text = vehicle.model.toString()
        holder.plateText.text = vehicle.plate
        holder.kmText.text = vehicle.km.toString()

    }
    override fun getItemCount(): Int {
        return list.size
    }

    fun updateList(newList: List<Vehicle>){
        list = newList
        notifyDataSetChanged()
    }
}