package com.example.carmaintenance.views.recyclerViewAdapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.carmaintenance.R
import com.example.carmaintenance.model.MaintenanceAction
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MaintenanceActionAdapter: RecyclerView.Adapter<MaintenanceActionAdapter.ViewHolder>() {
    private var list = listOf<MaintenanceAction>()

    class ViewHolder(view: View): RecyclerView.ViewHolder(view){
        val vehicleText: TextView = view.findViewById<TextView>(R.id.vehicleText)
        val actionText: TextView = view.findViewById<TextView>(R.id.actionText)
        val dateText: TextView = view.findViewById<TextView>(R.id.dateText)
        val kmExText: TextView = view.findViewById<TextView>(R.id.kmExText)
        val completeActionButton: FloatingActionButton = view.findViewById<FloatingActionButton>(R.id.completeActionButton)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.date_item_layout, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val action:MaintenanceAction = list[position]
        holder.vehicleText.text = action.plate
        holder.actionText.text = action.name
        holder.dateText.text = action.estimatedDate
        holder.completeActionButton.setOnClickListener{

        }

    }

    override fun getItemCount(): Int {
        return list.size

    }

    fun updateList(newList: List<MaintenanceAction>){
        list = newList
        notifyDataSetChanged()
    }

}