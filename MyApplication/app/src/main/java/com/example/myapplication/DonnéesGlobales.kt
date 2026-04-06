package com.example.myapplication

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.doublePreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "trackme_prefs")

class DataStoreManager(private val context: Context) {

    companion object {
        val DISTANCE_TOTALE_RUNNING = doublePreferencesKey("distance_totale_running")
        val DISTANCE_TOTALE_NATATION = doublePreferencesKey("distance_totale_natation")
        val DISTANCE_TOTALE_CYCLING = doublePreferencesKey("distance_totale_cycling")
    }

    // Running
    val distanceTotaleRunning: Flow<Double> = context.dataStore.data.map { prefs ->
        prefs[DISTANCE_TOTALE_RUNNING] ?: 0.0
    }

    suspend fun sauvegarderDistanceRunning(distance: Double) {
        context.dataStore.edit { prefs ->
            prefs[DISTANCE_TOTALE_RUNNING] = distance
        }
    }

    // Natation
    val distanceTotaleNatation: Flow<Double> = context.dataStore.data.map { prefs ->
        prefs[DISTANCE_TOTALE_NATATION] ?: 0.0
    }

    suspend fun sauvegarderDistanceNatation(distance: Double) {
        context.dataStore.edit { prefs ->
            prefs[DISTANCE_TOTALE_NATATION] = distance
        }
    }

    // Cycling
    val distanceTotaleCycling: Flow<Double> = context.dataStore.data.map { prefs ->
        prefs[DISTANCE_TOTALE_CYCLING] ?: 0.0
    }

    suspend fun sauvegarderDistanceCycling(distance: Double) {
        context.dataStore.edit { prefs ->
            prefs[DISTANCE_TOTALE_CYCLING] = distance
        }
    }
}