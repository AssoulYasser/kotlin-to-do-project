package com.example.roomtodolist.domain

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import com.example.roomtodolist.data.Repository
import com.example.roomtodolist.ui.screens.MainActivityScreen
import com.example.roomtodolist.ui.screens.MainViewModel
import com.example.roomtodolist.ui.theme.RoomToDoListTheme

@OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
class MainActivity : ComponentActivity() {
    private lateinit var context: Context
    private lateinit var repository: Repository
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        context = this
        repository = Repository(context)

        setContent {
            val mainViewModel = viewModel<MainViewModel>(
                factory = object: ViewModelProvider.Factory {
                    override fun <T : ViewModel> create(modelClass: Class<T>): T {
                        return MainViewModel(repository = repository) as T
                    }
                }
            )
            mainViewModel.setNavHostController(rememberNavController())
            mainViewModel.setWindowSizeClass(calculateWindowSizeClass(activity = this))
            RoomToDoListTheme {
                MainActivityScreen(mainViewModel = mainViewModel)
            }
        }
    }

    companion object{
        const val TAG = "DEBUGGING : "
    }
}