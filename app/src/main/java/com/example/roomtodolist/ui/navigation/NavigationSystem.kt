package com.example.roomtodolist.ui.navigation

import android.util.Log
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import java.util.Stack

class NavigationSystem(val navHostController: NavHostController) {

    private val navigationStack = Stack<String>()
    private var currentDestination = MainRoutes.HOME.name

    fun navigateTo(destination: String) {
        Log.d(TAG, "navigateTo current: $currentDestination")
        Log.d(TAG, "navigateTo: $navigationStack")
        if (currentDestination == destination)
            return

        else if (destination == MainRoutes.HOME.name) {
            navigationStack.clear()
            currentDestination = destination
        }

        else if (navigationStack.contains(destination)) {
            while (navigationStack.contains(destination)) {
                currentDestination = navigationStack.pop()
            }
        }

        else {
            navigationStack.push(currentDestination)
            currentDestination = destination
        }

        navigateTo()
    }

    fun navigateBack() {
        if (navigationStack.isNotEmpty())
            currentDestination = navigationStack.pop()
        navigateTo()
    }

    private fun navigateTo() {
        navHostController.navigate(currentDestination) {
            popUpTo(navHostController.graph.findStartDestination().id) {
                saveState = true
            }
            launchSingleTop = true
            restoreState = true
        }
    }

    companion object{
        private const val TAG = "DEBUGGING : "
    }

}