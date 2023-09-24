package com.example.roomtodolist.domain.main_activity

import android.net.Uri
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.example.roomtodolist.data.SharedPreferencesRepository

class ProfileManager(private val sharedPreferencesRepository: SharedPreferencesRepository) {


    var profilePictureState by mutableStateOf<Uri?>(null)
        private set

    var usernameState by mutableStateOf<String?>(null)
        private set

    var isDarkThemeState by mutableStateOf(false)
        private set


    private fun initProfile() {
        if (sharedPreferencesRepository.getProfilePicture() != null)
            profilePictureState = Uri.parse(sharedPreferencesRepository.getProfilePicture())

        usernameState = sharedPreferencesRepository.getUsername()

        isDarkThemeState = sharedPreferencesRepository.isDarkMode()
    }

    fun setUsername(name: String?) {
        usernameState = name ?: "UNNAMED"
        sharedPreferencesRepository.setUsername(name ?: "UNNAMED")
    }

    fun setProfilePicture(uri: Uri?) {
        profilePictureState = uri
        sharedPreferencesRepository.setProfilePicture(uri.toString())
    }

    fun setIsDarkMode(isDark: Boolean) {
        isDarkThemeState = isDark
        sharedPreferencesRepository.setMode(isDark)
    }

    init {
        initProfile()
    }

}