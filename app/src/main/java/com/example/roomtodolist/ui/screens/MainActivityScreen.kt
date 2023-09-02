package com.example.roomtodolist.ui.screens

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.windowsizeclass.WindowHeightSizeClass
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import com.example.roomtodolist.ui.screens.MainViewModel
import com.example.roomtodolist.ui.navigation.BottomBar
import com.example.roomtodolist.ui.navigation.ExpendedBar
import com.example.roomtodolist.ui.navigation.NavGraph
import com.example.roomtodolist.ui.navigation.SideBar

const val TAG = "DEBUGGING : "
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun MainActivityScreen(mainViewModel: MainViewModel) {
    @Composable
    fun Screen() {
        NavGraph(mainViewModel.navHostController)
    }

    @Composable
    fun Compact() {
        Box(modifier = Modifier.fillMaxSize()){
            Box(modifier = Modifier.padding(start = 50.dp)) {
                Screen()
            }
            Box(modifier = Modifier) {
                SideBar(navHostController = mainViewModel.navHostController)
            }
        }
    }

    @Composable
    fun Medium() {
        Box(modifier = Modifier.fillMaxSize()) {
            Box(modifier = Modifier.padding(bottom = 50.dp)) {
                Screen()
            }
            Box(modifier = Modifier.align(Alignment.BottomCenter)) {
                BottomBar(navHostController = mainViewModel.navHostController)
            }
        }
    }

    @Composable
    fun Expended() {
        Box(modifier = Modifier.fillMaxSize()) {
            Box(modifier = Modifier.padding(start = 200.dp)) {
                Screen()
            }
            Box(modifier = Modifier) {
                ExpendedBar(navHostController = mainViewModel.navHostController)
            }
        }
    }

    when(mainViewModel.windowSizeClass.widthSizeClass) {
        WindowWidthSizeClass.Compact, WindowWidthSizeClass.Medium -> {
            if (mainViewModel.windowSizeClass.heightSizeClass == WindowHeightSizeClass.Compact)
                Compact()
            else
                Medium()
        }

        WindowWidthSizeClass.Expanded -> {
            Expended()
        }
    }



}