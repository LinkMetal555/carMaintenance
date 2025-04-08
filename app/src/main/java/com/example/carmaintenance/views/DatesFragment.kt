package com.example.carmaintenance.views

import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.carmaintenance.R
import com.example.carmaintenance.viewmodel.DatesViewModel
import com.example.carmaintenance.views.recyclerViewAdapters.MaintenanceActionAdapter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DatesFragment : Fragment() {

    companion object {
        fun newInstance() = DatesFragment()
    }

    private val viewModel: DatesViewModel by viewModels()
    private lateinit var adapter: MaintenanceActionAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // TODO: Use the ViewModel
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.fragment_dates, container, false)

        val recyclerView = view.findViewById<RecyclerView>(R.id.dateRec)
        recyclerView.layoutManager = LinearLayoutManager(context)

        adapter = MaintenanceActionAdapter()
        recyclerView.adapter = adapter


        viewModel.maintenanceList.observe(viewLifecycleOwner) { list ->
            lifecycleScope.launch(Dispatchers.Main){
                adapter.updateList(list)
            }
        }

        viewModel.loadMaintenanceAndDocuments()

        return view

    }
}