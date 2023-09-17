package com.example.roomtodolist.domain.set_up_activity

import android.annotation.SuppressLint
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.roomtodolist.R
import com.example.roomtodolist.data.SharedPreferencesRepository

@SuppressLint("StaticFieldLeak")
class SetUpViewModel(
    private val sharedPreferencesRepository: SharedPreferencesRepository,
    private val activity: SetUpActivity
): ViewModel() {

    lateinit var windowSizeClass: WindowSizeClass
        private set

    var uiState by mutableStateOf(
        SetUpUiState(
            isFirstAccess = sharedPreferencesRepository.isFirstTimeAccess(),
            isSetUp = sharedPreferencesRepository.hasUserSetUpProfile()
        )
    )
        private set



    init {
        if (uiState.isSetUp)
            startMainActivity()
    }

    fun setWindowSizeClass(windowSizeClass: WindowSizeClass) {
        this.windowSizeClass = windowSizeClass
    }

    fun setFirstAccess() {
        uiState = uiState.copy(isFirstAccess = false)
        sharedPreferencesRepository.firstAccess()
    }

    fun setMode(isDark: Boolean) {
        sharedPreferencesRepository.setMode(isDark)
    }

    fun setProfilePicture(uri: String) {
        uiState = uiState.copy(profilePicture = uri)
    }

    fun setUserName(name: String) {
        uiState = uiState.copy(username = name)
    }

    private fun startMainActivity() {
        activity.goToMainActivity()
    }

    fun setUpProfile() {
        sharedPreferencesRepository.setUsername(uiState.username)
        sharedPreferencesRepository.setProfilePicture(uiState.profilePicture)
        sharedPreferencesRepository.setUpUserProfile()
        startMainActivity()
    }


    val onBoardingDataList = listOf(
        OnBoardingData(
            title = "Welcome to To Do",
            description = "The simplest and most powerful way " +
                    "to manage your tasks and projects",
            image = R.drawable.onboarding_asset_1
        ),
        OnBoardingData(
            title = "There is so much To Do",
            description = "Create lists, add tasks, set deadlines, " +
                    "assign priorities, and track your progress with ease",
            image = R.drawable.onboarding_asset_2
        ),
        OnBoardingData(
            title = "Ready to get started ?",
            description = "Start manage your project with To Do",
            image = R.drawable.onboarding_asset_3
        ),
    )
}