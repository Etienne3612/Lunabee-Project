package com.example.myapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            var page by remember { mutableStateOf(1) }//variable qui retient sur quelle page nous nous situons
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