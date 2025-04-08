package com.example.carmaintenance.repository

import android.util.Log
import com.example.carmaintenance.model.FirebaseServicesManager
import com.example.carmaintenance.model.MaintenanceAction
import com.example.carmaintenance.model.Vehicle
import kotlinx.coroutines.tasks.await
import java.time.LocalDate

object VehiclesRepository {
    private const val TAG: String = "VehiclesRepository"
    private val sM: FirebaseServicesManager = FirebaseServicesManager


    suspend fun getVehicle(vehicleId: String): Vehicle {
        val vehicle = Vehicle()
        return try {

            val result = sM.db.collection("vehicles").document(vehicleId).get().await()

            if(result.exists()){
                vehicle.km = result.getLong("km")!!.toInt()
                vehicle.model = result.getLong("model")!!.toInt()
                vehicle.builder = result.getString("builder").toString()
                vehicle.plate = result.getString("plate").toString()
                vehicle.avgKm = result.getLong("avgKm")!!.toInt()
            }
            Log.d(TAG, "Vehicle data pulled from the database correctly, $vehicle")
            vehicle
        }
        catch (e: Exception) {
            Log.e(TAG, "Vehicle data cannot be pulled from the database", e)
            vehicle
        }

            /*if (vehicleDataTask.isSuccessful){
                Log.d(TAG, "Vehicle data pulled from the database correctly")
                val result = vehicleDataTask.result
                vehicle.name = result.getString("name").toString()
                vehicle.km = result.getLong("km")!!.toInt()
                vehicle.model = result.getLong("model")!!.toInt()
                vehicle.builder = result.getString("builder").toString()
                vehicle.plate = result.getString("plate").toString()
                vehicle.avgKm = result.getLong("avgKm")!!.toInt()

            }
            else{
                Log.w(TAG, "Vehicle data cannot be pulled from the database", vehicleDataTask.exception)
            }
        }

        return vehicle*/
    }

    //TODO Use this with an use case to add it to the user vehicles array
    fun postVehicle(vehicle: Vehicle, country:String){
        sM.db.collection("vehicles").document(country+"-"+vehicle.plate).set(vehicle).addOnCompleteListener { vehicleSetTask->
            if (vehicleSetTask.isSuccessful){
                Log.d(TAG, "Vehicle data persisted on database correctly")
            }
            else{
                Log.w(TAG, "Vehicle data cannot be persisted on the database", vehicleSetTask.exception)
            }
        }
    }



}