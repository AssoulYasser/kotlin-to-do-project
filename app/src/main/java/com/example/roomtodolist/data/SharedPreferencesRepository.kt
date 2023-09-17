package com.example.roomtodolist.data

import android.content.Context
import android.content.Context.MODE_PRIVATE

class SharedPreferencesRepository(private val context: Context) {

    companion object {
        private const val USER_SHARED_PREFERENCES = "USER"
        private const val FIRST_TIME_KEY = "FIRST_TIME_KEY"
        private const val SET_UP_KEY = "SET_UP_KEY"
        private const val PROFILE_PICTURE_KEY = "PROFILE_PICTURE_KEY"
        private const val USERNAME_KEY = "USERNAME_KEY"
        private const val IS_DARK_MODE = "IS_DARK_MODE"
    }

    private val sharedPreferences = context.getSharedPreferences(USER_SHARED_PREFERENCES, MODE_PRIVATE)
    private val editor = sharedPreferences.edit()

    fun isFirstTimeAccess() : Boolean = sharedPreferences.getBoolean(FIRST_TIME_KEY, true)

    fun firstAccess() {
        editor.apply{
            putBoolean(FIRST_TIME_KEY, false)
        }.apply()
    }

    fun hasUserSetUpProfile() : Boolean = sharedPreferences.getBoolean(SET_UP_KEY, false)

    fun setUpUserProfile() {
        editor.apply{
            putBoolean(SET_UP_KEY, true)
        }.apply()
    }

    fun setProfilePicture(base64Image: String?) {
        editor.apply{
            putString(PROFILE_PICTURE_KEY, base64Image)
        }.apply()
    }

    fun getProfilePicture() : String? = sharedPreferences.getString(PROFILE_PICTURE_KEY, null)

    fun setUsername(username: String?) {
        editor.apply{
            putString(USERNAME_KEY, username)
        }.apply()
    }

    fun getUsername() : String? = sharedPreferences.getString(USERNAME_KEY, null)

    fun setMode(isDark: Boolean) {
        editor.apply {
            putBoolean(IS_DARK_MODE, isDark)
        }.apply()
    }

    fun isDarkMode() : Boolean = sharedPreferences.getBoolean(IS_DARK_MODE, false)

}