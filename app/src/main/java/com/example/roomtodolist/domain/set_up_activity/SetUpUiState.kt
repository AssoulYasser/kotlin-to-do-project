package com.example.roomtodolist.domain.set_up_activity

data class SetUpUiState(
    val isFirstAccess: Boolean,
    val isSetUp: Boolean,
    val profilePicture: String? = null,
    val username: String? = null
)
