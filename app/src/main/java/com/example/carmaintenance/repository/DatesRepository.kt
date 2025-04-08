package com.example.carmaintenance.repository

import android.util.Log
import com.example.carmaintenance.model.CarDocument
import com.example.carmaintenance.model.FirebaseServicesManager
import com.example.carmaintenance.model.MaintenanceAction
import kotlinx.coroutines.tasks.await
import java.time.LocalDate

object DatesRepository {
    private const val TAG: String = "DatesRepository"
    private val sM: FirebaseServicesManager = FirebaseServicesManager


    suspend fun getMaintenanceActionsAndDocumentsFromVehicle(vehicleId: String): Pair<List<MaintenanceAction>,List<CarDocument>> {
        var maintenanceList = emptyList<MaintenanceAction>()
        var documentList = emptyList<CarDocument>()
        return try {
            val result = sM.db.collection("vehicles").document(vehicleId).get().await()
            if (result.exists()) {
                Log.d(TAG, "Vehicle data pulled from the database correctly")

                val km = result.getLong("km")!!.toInt()

                val lastKm = (result.get("lastKm") as List<*>).filterIsInstance<Int>()
                val maintenanceDoc: List<Map<String, Any>> =
                    (result.get("maintenance") as List<*>).filterIsInstance<Map<String, Any>>()

                maintenanceList = maintenanceDoc.map { action ->
                    val name = action["name"] as String
                    val frecuency = (action["frecuency"] as Long).toInt()
                    val lastChange = (action["lastChange"] as Long).toInt()
                    val kmLeft = (lastChange + frecuency) - km
                    val daysLeft = kmLeft / lastKm.average()
                    val estimatedDate =
                        LocalDate.now().plusDays(daysLeft.toLong()).toString() //Local date

                    MaintenanceAction(
                        plate = vehicleId.split("-")[1],
                        name,
                        frecuency,
                        estimatedDate
                    )
                }

                val documentDoc: List<Map<String, Any>> =
                    (result.get("documents") as List<*>).filterIsInstance<Map<String, Any>>()
                documentList = documentDoc.map { document ->
                    val name = document["name"] as String
                    val date = document["date"].toString()

                    CarDocument(name, date)
                }

            }
            Pair(maintenanceList, documentList)
        } catch (e: Exception) {
            Log.e(
                TAG,
                "Vehicle maintenance and documents data cannot be pulled from the database",
                e
            )
            Pair(maintenanceList, documentList)
        }
    }
}