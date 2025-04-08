package com.example.carmaintenance.repository

import android.util.Log
import com.example.carmaintenance.model.FirebaseServicesManager
import com.example.carmaintenance.model.User
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.DocumentSnapshot
import kotlinx.coroutines.tasks.await

object UserRepository {
    private lateinit var currentUser: FirebaseUser
    private lateinit var userID: String
    private const val TAG: String = "UserRepository"
    private val userInstance: User = User()
    private val sM:FirebaseServicesManager = FirebaseServicesManager

    fun registerUser(name:String, email:String, password:String):Boolean{
        val complete = false
        sM.auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener { authTask ->
            if (authTask.isSuccessful){

                Log.d(TAG, "User register successful")
                currentUser = sM.auth.currentUser!!
                userID = currentUser.uid
                userInstance.name = name

                sM.db.collection("users").document(userID).set(userInstance).addOnCompleteListener { dbTask ->
                    if (dbTask.isSuccessful){
                        Log.d(TAG, "User data persisted on database")
                        complete.not()
                    }
                    else {
                        Log.w(TAG, "User data was not persisted to database", dbTask.exception)
                    }
                }
            }
            else {
                Log.w(TAG, "User register failed", authTask.exception)
            }
        }
        return complete
    }

    suspend fun loginUser(email: String, password: String): Boolean {
        Log.d(TAG, "Trying to login")

        return try {
            val authResult = sM.auth.signInWithEmailAndPassword(email, password).await() // Espera el resultado

            currentUser = authResult.user ?: throw Exception("User is null after login")
            userID = currentUser.uid

            Log.d(TAG, "User id: $userID")

            val doc = sM.db.collection("users").document(userID).get().await() // Ahora sí puedes usar await

            if (doc.exists()) {
                Log.d(TAG, "User data pulled from the database correctly")

                userInstance.name = doc.getString("name") ?: ""
                userInstance.vehicles = (doc.get("vehicles") as? List<*>)?.filterIsInstance<String>() ?: emptyList()

                Log.d(TAG, "User name: ${userInstance.name}")
                Log.d(TAG, "User vehicles: ${userInstance.vehicles}")

                true // Retorna `true` si el inicio de sesión fue exitoso
            } else {
                Log.e(TAG, "User data cannot be pulled from the database")
                false
            }
        } catch (e: Exception) {
            Log.e(TAG, "User login failed", e)
            false
        }
    }

    fun getUserVehicles(): List<String> {
        Log.d(TAG, "User vehicles from getter: ${userInstance.vehicles}")
        return userInstance.vehicles

    }

    fun getUserInstance():User{

        return userInstance
    }


}