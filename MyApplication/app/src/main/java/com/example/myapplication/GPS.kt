package com.example.myapplication

import android.annotation.SuppressLint
import android.content.Context
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority

class GPS(context: Context) {

    private var derniereLat = 0.0
    private var derniereLon = 0.0
    var distanceTotale = 0.0

    private val fusedLocationClient = LocationServices.getFusedLocationProviderClient(context)

    private val locationRequest = LocationRequest.Builder(5000)
        .setMinUpdateIntervalMillis(2000)
        .setPriority(Priority.PRIORITY_HIGH_ACCURACY)
        .build()

    private var onLocationUpdate: ((Double, Double, Double, Float, Double) -> Unit)? = null

    private val locationCallback = object : LocationCallback() {
        override fun onLocationResult(result: LocationResult) {
            val position = result.lastLocation ?: return

            if (derniereLat != 0.0 && derniereLon != 0.0) {
                val resultat = FloatArray(1)
                android.location.Location.distanceBetween(
                    derniereLat, derniereLon,
                    position.latitude, position.longitude,
                    resultat
                )
                distanceTotale += resultat[0]
            }

            derniereLat = position.latitude
            derniereLon = position.longitude

            onLocationUpdate?.invoke(
                position.latitude,
                position.longitude,
                position.altitude,
                position.speed,
                distanceTotale
            )
        }
    }

    @SuppressLint("MissingPermission")
    fun startTracking(onLocationUpdate: (Double, Double, Double, Float, Double) -> Unit) {
        this.onLocationUpdate = onLocationUpdate
        fusedLocationClient.requestLocationUpdates(
            locationRequest,
            locationCallback,
            android.os.Looper.getMainLooper()
        )
    }

    fun stopTracking() {
        fusedLocationClient.removeLocationUpdates(locationCallback)
        onLocationUpdate = null
    }
}