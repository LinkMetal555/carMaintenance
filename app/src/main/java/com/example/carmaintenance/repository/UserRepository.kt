package com.example.carmaintenance.repository

import android.util.Log
import com.example.carmaintenance.model.FirebaseServicesManager
import com.example.carmaintenance.model.User
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.DocumentSnapshot

object UserRepository {
    private lateinit var currentUser: FirebaseUser
    private lateinit var userID: String
    private const val TAG: String = "UserRepository"
    private val userInstance: User = User()
    private val sM:FirebaseServicesManager = FirebaseServicesManager

    fun registerUser(name:String, email:String, password:String){

        sM.auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener { authTask ->
            if (authTask.isSuccessful){

                Log.d(TAG, "User register successful")
                currentUser = sM.auth.currentUser!!
                userID = currentUser.uid
                userInstance.name = name

                sM.db.collection("users").document(userID).set(userInstance).addOnCompleteListener { dbTask ->
                    if (dbTask.isSuccessful){
                        Log.d(TAG, "User data persisted on database")
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
    }

    fun loginUser(email: String,password: String){
        Log.d(TAG, "Trying to login")
        sM.auth.signInWithEmailAndPassword(email, password).addOnCompleteListener { task ->
            if (task.isSuccessful){

                currentUser = sM.auth.currentUser!!
                userID = currentUser.uid

                Log.d(TAG, "User id: $userID")

                sM.db.collection("users").document(userID).get().addOnCompleteListener {userDataTask->
                    if (userDataTask.isSuccessful){
                        Log.d(TAG, "User data pulled from the database correctly")
                        val doc: DocumentSnapshot = userDataTask.result
                        userInstance.name = doc.getString("name")!!
                        userInstance.vehicles = (doc.get("vehicles") as List<*>).filterIsInstance<String>()
                    }
                    else{
                        Log.w(TAG, "User data cannot be pulled from the database", userDataTask.exception)
                    }

                }
            }
            else {
                Log.w(TAG, "User login failed", task.exception)
            }
        }
    }

    fun getUserVehicles(): List<String> {
        return userInstance.vehicles

    }


}