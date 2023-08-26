package com.example.roomtodolist.ui.screens

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
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
    Log.d(TAG, if (LocalConfiguration.current.screenHeightDp >= LocalConfiguration.current.screenWidthDp) "portrait" else "landscape")
    Log.d(TAG, "MainActivityScreen: ${windowSize.widthSizeClass}")
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
                Row(
                    modifier = Modifier.fillMaxSize(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Start
                ) {
                    SideBar(navHostController = navController)
                    NavGraph(navHostController = navController, windowSizeClass = windowSize)
                }
            }
            WindowWidthSizeClass.Expanded -> {
                Row(
                    modifier = Modifier.fillMaxSize(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Start
                ) {
                    ExpendedBar(navHostController = navController)
                    NavGraph(navHostController = navController, windowSizeClass = windowSize)
                }
            }
        }
    }


}