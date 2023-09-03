package com.example.roomtodolist.ui.screens.home

import androidx.lifecycle.ViewModel
import com.example.roomtodolist.ui.navigation.NestedRoutes
import com.example.roomtodolist.ui.screens.MainViewModel

class HomeViewModel(private val mainViewModel: MainViewModel) : ViewModel() {

    fun navigateToAddFolderScreen() {
        mainViewModel.navigateTo(NestedRoutes.ADD_FOLDER.name)
    }

}