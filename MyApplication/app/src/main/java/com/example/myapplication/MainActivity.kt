package com.example.myapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.sp


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            var page by remember { mutableStateOf(1) }
            when (page) {
                1 -> AccueilScreen(
                    onNavigate = {page = 1},
                    onNavigateRunning  = {page = 2},
                    onNavigateSwimming = {page = 3},
                    onNavigateCycling = {page = 4},
                )
                2 -> RunningScreen(onNavigate =  {page = 1})
                3 -> SwimmingScreen(onNavigate = {page = 1})
                4 -> CyclingScreen(onNavigate =  {page = 1})
            }
        }
    }
}