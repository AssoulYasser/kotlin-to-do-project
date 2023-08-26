package com.example.roomtodolist.ui.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.roomtodolist.ui.navigation.BottomBar
import com.example.roomtodolist.ui.navigation.ExpendedBar
import com.example.roomtodolist.ui.navigation.NavGraph
import com.example.roomtodolist.ui.navigation.SideBar

const val TAG = "DEBUGGING : "
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun MainActivityScreen(windowSize: WindowSizeClass, navController: NavHostController) {
    if (LocalConfiguration.current.screenHeightDp >= LocalConfiguration.current.screenWidthDp) {
        Box(modifier = Modifier.fillMaxSize()) {
            Box(modifier = Modifier.padding(bottom = 50.dp)) {
                NavGraph(navHostController = navController, windowSizeClass = windowSize)
            }
            Box(modifier = Modifier.align(Alignment.BottomCenter)) {
                BottomBar(navHostController = navController)
            }
        }
    } else {
        when(windowSize.widthSizeClass) {
            WindowWidthSizeClass.Compact, WindowWidthSizeClass.Medium -> {
                Box(modifier = Modifier.fillMaxSize()){
                    Box(modifier = Modifier.padding(start = 50.dp)) {
                        NavGraph(navHostController = navController, windowSizeClass = windowSize)
                    }
                    Box(modifier = Modifier) {
                        SideBar(navHostController = navController)
                    }
                }
            }
            WindowWidthSizeClass.Expanded -> {
                Box(modifier = Modifier.fillMaxSize()) {
                    Box(modifier = Modifier.padding(start = 200.dp)) {
                        NavGraph(navHostController = navController, windowSizeClass = windowSize)
                    }
                    Box(modifier = Modifier) {
                        ExpendedBar(navHostController = navController)
                    }
                }
            }
        }
    }


}