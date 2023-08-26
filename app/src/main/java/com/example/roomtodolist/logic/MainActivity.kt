package com.example.roomtodolist.logic

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.navigation.compose.rememberNavController
import com.example.roomtodolist.ui.screens.MainActivityScreen
import com.example.roomtodolist.ui.theme.RoomToDoListTheme

@OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
class MainActivity : ComponentActivity() {
    private lateinit var context: Context
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        context = this

        setContent {
            val windowSizeClass = calculateWindowSizeClass(activity = this)
            val navigationController = rememberNavController()
            RoomToDoListTheme {
                MainActivityScreen(
                    windowSize = windowSizeClass,
                    navController = navigationController
                )
            }
        }
    }

    companion object{
        const val TAG = "DEBUGGING : "
    }
}