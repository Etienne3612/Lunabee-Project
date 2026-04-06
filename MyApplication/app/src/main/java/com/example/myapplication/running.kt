package com.example.myapplication
import androidx.compose.foundation.background
import android.Manifest
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.runtime.rememberCoroutineScope
import kotlinx.coroutines.launch
@Composable
fun RunningScreen(onNavigate: () -> Unit) {
    val context = LocalContext.current // On accède aux ressources d'Android
    val dataStoreManager = remember { DataStoreManager(context) }
    val scope = rememberCoroutineScope()
    val distanceRunningExistante by dataStoreManager.distanceTotaleRunning.collectAsState(initial = 0.0)//On récupère l'ancienne valeur de la distance cumulée de running

    var is_running by remember { mutableStateOf(false) }

    var distanceTotale by remember { mutableStateOf(0.0) }
    var latitude by remember { mutableStateOf(0.0) }
    var longitude by remember { mutableStateOf(0.0) }
    var altitude by remember { mutableStateOf(0.0) }
    var vitesse by remember { mutableStateOf(0f) }


    val gps = remember { GPS(context) }

    val permissionLauncher = rememberLauncherForActivityResult(//On récupère la permission GPS
        ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (isGranted) {
            gps.startTracking { lat, lon, alt, spd, dst ->
                latitude = lat
                longitude = lon
                altitude = alt
                vitesse = spd
                distanceTotale = dst
            }
        }
    }
    Box(
        modifier = Modifier.fillMaxSize()
        .fillMaxSize()
        .background(Color(0xFF1D9E75))
    ) {


        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Box(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth(0.8f)
                    .background(Color.White, shape = RoundedCornerShape(16.dp))
                    .padding(16.dp) // espace intérieur
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        text = "Distance : ${"%.1f".format(distanceTotale / 1000)} km",
                        fontSize = 20.sp,
                        color = Color.Black
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = if (vitesse > 0 && vitesse < 12) "Vitesse : ${"%.1f".format(60 / vitesse)} min/km" else "Vitesse : -- min/km",
                        fontSize = 20.sp,
                        color = Color.Black
                    )
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            Button(onClick = {
                if (!is_running) {
                    is_running = true
                    permissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
                } else {
                    gps.stopTracking()
                    is_running = false
                    scope.launch {
                        dataStoreManager.sauvegarderDistanceRunning(distanceTotale + distanceRunningExistante)
                    }
                    onNavigate()
                }
            }) {
                Text(if (!is_running) "Démarrer l'activité" else "Terminer l'activité")
            }
        }
    }
}