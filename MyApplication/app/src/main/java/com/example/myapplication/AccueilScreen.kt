package com.example.myapplication

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun AccueilScreen(
    onNavigate: () -> Unit,
    onNavigateRunning: () -> Unit,
    onNavigateSwimming: () -> Unit,
    onNavigateCycling: () -> Unit,
) {
    val context = LocalContext.current
    val dataStoreManager = remember { DataStoreManager(context) }
    val distanceRunning by dataStoreManager.distanceTotaleRunning.collectAsState(initial = 0.0)
    val distanceNatation by dataStoreManager.distanceTotaleNatation.collectAsState(initial = 0.0)
    val distanceCycling by dataStoreManager.distanceTotaleCycling.collectAsState(initial = 0.0)

    Box(modifier = Modifier.fillMaxSize()) {
        Image(
            painter = painterResource(id = R.drawable.fond),
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )


        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(40.dp))

            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(text = "Running", fontSize = 24.sp, color = Color(0xFF1D9E75))
                    Text(text = "${"%.1f".format(distanceRunning / 1000)} km", fontSize = 16.sp)
                }

                Text(text = " | ", fontSize = 24.sp)

                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(text = "Swimming", fontSize = 24.sp, color = Color(0xFF378ADD))
                    Text(text = "${"%.1f".format(distanceNatation / 1000)} km", fontSize = 16.sp)
                }

                Text(text = " | ", fontSize = 24.sp)

                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(text = "Bike", fontSize = 24.sp, color = Color(0xFFBA7517))
                    Text(text = "${"%.1f".format(distanceCycling / 1000)} km", fontSize = 16.sp)
                }
            }

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 32.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Button(
                    onClick = { onNavigateRunning() },
                    modifier = Modifier.fillMaxWidth().height(60.dp),
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF1D9E75))
                ) {
                    Text("Running", fontSize = 18.sp)
                }

                Button(
                    onClick = { onNavigateSwimming() },
                    modifier = Modifier.fillMaxWidth().height(60.dp),
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF378ADD))
                ) {
                    Text("Swimming", fontSize = 18.sp)
                }

                Button(
                    onClick = { onNavigateCycling() },
                    modifier = Modifier.fillMaxWidth().height(60.dp),
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFBA7517))
                ) {
                    Text("Bike", fontSize = 18.sp)
                }
            }
        }
    }
}