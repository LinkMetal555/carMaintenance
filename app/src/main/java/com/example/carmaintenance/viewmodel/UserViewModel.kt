package com.example.carmaintenance.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.carmaintenance.model.User
import com.example.carmaintenance.repository.UserRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class UserViewModel: ViewModel() {

    private val repository: UserRepository = UserRepository

    val authState = MutableLiveData<Boolean>(false)
    val userEmail = MutableLiveData<String>()
    val userVehicles = MutableLiveData<List<String>>()

    fun authUser(email:String, password:String, name:String?, reg:Boolean){
        viewModelScope.launch(Dispatchers.IO){
            val auth:Boolean

            if (reg){
                Log.d(TAG, "Registering user")
                auth = repository.registerUser(name!!, email, password)
            } else{
                Log.d(TAG, "Logging user")
                auth = repository.loginUser(email = email, password = password)
                userVehicles.postValue(repository.getUserVehicles())
            }
            userEmail.postValue(email)

            Log.d(TAG, "Auth value: $auth")
            authState.postValue(auth)
        }

    }

    companion object {
        private const val TAG: String = "UserViewModel"
    }

}