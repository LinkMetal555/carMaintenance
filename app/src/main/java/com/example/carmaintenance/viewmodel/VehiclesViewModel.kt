package com.example.carmaintenance.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.carmaintenance.model.Vehicle
import com.example.carmaintenance.repository.UserRepository
import com.example.carmaintenance.repository.VehiclesRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class VehiclesViewModel : ViewModel() {

    val vehiclesList = MutableLiveData<List<Vehicle>>()

    private val repository: VehiclesRepository = VehiclesRepository

    private var vehiclesIds: List<String> = UserRepository.getUserVehicles()

    fun loadVehicles(){
        println("VEHICLES IDS: $vehiclesIds")
        val vehicles = mutableListOf<Vehicle>()
        viewModelScope.launch(Dispatchers.IO) {
            vehiclesIds.forEach { id ->
                val vehicle = repository.getVehicle(id)
                Log.d(TAG, "Vehicle on the for each: $vehicle")
                vehicles.add(vehicle)
            }
            Log.d(TAG,"FINAL VEHICLES: $vehicles")
            vehiclesList.postValue(vehicles.toList())
        }

    }

    companion object {
        private const val TAG: String = "VehiclesViewModel"
    }
}