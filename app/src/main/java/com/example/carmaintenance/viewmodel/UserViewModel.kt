package com.example.carmaintenance.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.carmaintenance.repository.UserRepository

class UserViewModel: ViewModel() {

    private val repository: UserRepository = UserRepository

    val userName = MutableLiveData<String>()
    val userEmail = MutableLiveData<String>()
    val userVehicles = MutableLiveData<List<String>>()

    fun authUser(email:String, password:String, name:String?, reg:Boolean){
        if (reg){
            repository.registerUser(name!!, email, password)
        }
        else{
            repository.loginUser(email = email, password = password)
        }

        userEmail.postValue(email)
        userVehicles.postValue(repository.getUserVehicles())

    }

}