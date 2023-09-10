package com.example.roomtodolist.domain

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.windowsizeclass.WindowHeightSizeClass
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.roomtodolist.ui.navigation.navbar.BottomBar
import com.example.roomtodolist.ui.navigation.navbar.ExpendedBar
import com.example.roomtodolist.ui.navigation.NavGraph
import com.example.roomtodolist.ui.navigation.navbar.SideBar

const val TAG = "DEBUGGING : "
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun MainActivityScreen(mainViewModel: MainViewModel) {
    @Composable
    fun Screen() {
        NavGraph(mainViewModel)
    }

    @Composable
    fun Compact() {
        Box(modifier = Modifier.fillMaxSize()){
            Box(modifier = Modifier.padding(start = 50.dp)) {
                Screen()
            }
            Box(modifier = Modifier) {
                SideBar(mainViewModel = mainViewModel)
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
                BottomBar(mainViewModel = mainViewModel)
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
                ExpendedBar(mainViewModel = mainViewModel)
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


    mainViewModel.start()



}