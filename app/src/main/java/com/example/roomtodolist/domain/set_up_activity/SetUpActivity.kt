package com.example.roomtodolist.domain.set_up_activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.Button
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.roomtodolist.data.SharedPreferencesRepository
import com.example.roomtodolist.domain.main_activity.MainActivity
import com.example.roomtodolist.ui.theme.RoomToDoListTheme

class SetUpActivity : ComponentActivity() {
    private lateinit var context: Context
    private lateinit var sharedPreferencesRepository: SharedPreferencesRepository

    @OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {

        context = this
        sharedPreferencesRepository = SharedPreferencesRepository(context)

        super.onCreate(savedInstanceState)
        setContent {
            val setUpViewModel = viewModel<SetUpViewModel>(
                factory = object: ViewModelProvider.Factory {
                    override fun <T : ViewModel> create(modelClass: Class<T>): T {
                        return SetUpViewModel(
                            sharedPreferencesRepository = sharedPreferencesRepository
                        ) as T
                    }
                }
            )
            setUpViewModel.setWindowSizeClass(calculateWindowSizeClass(activity = this))
            RoomToDoListTheme {
                SetUpActivityScreen(setUpViewModel = setUpViewModel)
            }
        }
    }
}