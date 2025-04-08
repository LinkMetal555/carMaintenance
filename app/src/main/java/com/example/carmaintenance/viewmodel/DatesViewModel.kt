package com.example.carmaintenance.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.carmaintenance.model.CarDocument
import com.example.carmaintenance.model.MaintenanceAction
import com.example.carmaintenance.repository.DatesRepository
import com.example.carmaintenance.repository.UserRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class DatesViewModel : ViewModel() {
    val maintenanceList = MutableLiveData<List<MaintenanceAction>>()
    val documentList = MutableLiveData<List<CarDocument>>()

    private val repository: DatesRepository = DatesRepository
    private var vehiclesIds: List<String> = UserRepository.getUserVehicles()

    fun loadMaintenanceAndDocuments(){

        val maintenance = mutableListOf<MaintenanceAction>()
        val documents = mutableListOf<CarDocument>()
        viewModelScope.launch(Dispatchers.IO) {
            vehiclesIds.forEach { id ->
                withContext(Dispatchers.Default){
                    val maintenanceAndDocuments =
                        repository.getMaintenanceActionsAndDocumentsFromVehicle(id)
                    maintenance.addAll(maintenanceAndDocuments.first)
                    documents.addAll(maintenanceAndDocuments.second)
                }

            }
            maintenanceList.postValue(maintenance.toList())
            documentList.postValue(documents.toList())
        }
    }
}