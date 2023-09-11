package com.example.roomtodolist.domain.main_activity

import android.content.Context
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import com.example.roomtodolist.data.DatabaseRepository
import com.example.roomtodolist.ui.theme.RoomToDoListTheme
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
class MainActivity : ComponentActivity() {
    private lateinit var context: Context
    private lateinit var databaseRepository: DatabaseRepository
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        context = this
        databaseRepository = DatabaseRepository(context)

        setContent {
            val mainViewModel = viewModel<MainViewModel>(
                factory = object: ViewModelProvider.Factory {
                    override fun <T : ViewModel> create(modelClass: Class<T>): T {
                        return MainViewModel(databaseRepository = databaseRepository) as T
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