package com.example.carmaintenance.model

import com.google.firebase.Timestamp

data class MaintenanceAction(var frecuency: Int,var lastChange: Int, var lastChangeDate: Timestamp)
