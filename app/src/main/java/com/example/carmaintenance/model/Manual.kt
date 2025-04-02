package com.example.carmaintenance.model

import com.google.firebase.Timestamp

data class Manual(
    var ac:MaintenanceAction,
    var acFilter:MaintenanceAction,
    var accesoriesEngineBelt:MaintenanceAction,
    var airFilter:MaintenanceAction,
    var alternator:MaintenanceAction,
    var backBrakes:MaintenanceAction,
    var bodyworkWash:MaintenanceAction,
    var breakFluid:MaintenanceAction,
    var engineWash:MaintenanceAction,
    var frontBrakes:MaintenanceAction,
    var fuelFilter:MaintenanceAction,
    var coolant:MaintenanceAction,
    var oilEngine:MaintenanceAction,
    var radiator:MaintenanceAction,
    var sparkPlugs:MaintenanceAction,
    var steering:MaintenanceAction,
    var suspension:MaintenanceAction,
    var tires:MaintenanceAction,
    var transmissionOil:MaintenanceAction,
    var rtm: Timestamp,
    var soat: Timestamp,
    var tax:Timestamp
    )

