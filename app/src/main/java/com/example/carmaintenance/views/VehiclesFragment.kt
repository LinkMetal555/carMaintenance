package com.example.carmaintenance.views

import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.carmaintenance.R
import com.example.carmaintenance.viewmodel.VehiclesViewModel
import com.example.carmaintenance.views.recyclerViewAdapters.VehicleAdapter

class VehiclesFragment : Fragment() {

    companion object {
        fun newInstance() = VehiclesFragment()
    }

    private val viewModel: VehiclesViewModel by viewModels()
    private val TAG :String = "VehiclesFragment"
    private lateinit var adapter: VehicleAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.fragment_vehicles, container, false)
        viewModel.loadVehicles()
        val recyclerView = view.findViewById<RecyclerView>(R.id.vehiclesRec)
        recyclerView.layoutManager = LinearLayoutManager(context)

        adapter = VehicleAdapter()
        recyclerView.adapter = adapter

        viewModel.vehiclesList.observe(viewLifecycleOwner) { list ->
            adapter.updateList(list)
        }

        return view
    }
}