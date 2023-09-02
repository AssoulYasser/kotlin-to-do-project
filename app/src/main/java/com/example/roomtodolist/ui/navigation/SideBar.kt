package com.example.roomtodolist.ui.navigation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.selection.selectable
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState

@Composable
fun SideBar(navHostController: NavHostController) {
    val navBackStackEntry by navHostController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    SideNavLayout {
        navigationList.forEach{ nav ->
            val navDestination = nav.mainDestination
            NavElement(
                modifier = Modifier,
                element = navDestination,
                isSelected = currentDestination?.hierarchy?.any { it.route == navDestination.route.name } == true
            ) {
                navHostController.navigateTo(navDestination.route.name)
            }
        }
    }

}

@Composable
fun SideNavLayout(
    backgroundColor: Color = MaterialTheme.colorScheme.background,
    navWidth: Dp = 50.dp,
    elevation: Dp = 8.dp,
    content: @Composable ColumnScope.() -> Unit
) {
    Surface(
        color = backgroundColor,
        shadowElevation = elevation
    ) {
        Column(
            modifier = Modifier
                .width(navWidth)
                .fillMaxHeight(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceEvenly,
            content = content
        )
    }
}

@Composable
private fun ColumnScope.NavElement(
    modifier: Modifier = Modifier,
    element: MainDestinations,
    isSelected: Boolean = false,
    selectedColor: Color = MaterialTheme.colorScheme.primary,
    unselectedColor: Color = MaterialTheme.colorScheme.onBackground,
    onClick: () -> Unit
) {
    Box(modifier = modifier
        .weight(1f)
        .fillMaxWidth()
        .selectable(
            selected = true,
            enabled = true,
            role = Role.Tab,
            onClick = {
                onClick()
            }
        )
    ) {
        Icon(
            painter = painterResource(id = if (isSelected) element.icon.selected else element.icon.unselected),
            contentDescription = null,
            tint = if (isSelected) selectedColor else unselectedColor.copy(alpha = 0.2f),
            modifier = Modifier.align(Alignment.Center)
        )
    }

}
