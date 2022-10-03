package com.example.workoutapp.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.workoutapp.routes.Screen
import com.example.workoutapp.ui.theme.darkBlue
import com.example.workoutapp.ui.theme.offWhite

//Creates the bottom navigation bar with icons
@Composable
fun BottomNavBar(navController: NavController){
    //Row to put all the icons in
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxSize(.08f)
            .background(darkBlue),
        horizontalArrangement = Arrangement.SpaceEvenly
    ){
        //Box for Home Screen
        Box(
            modifier = Modifier.fillMaxHeight(),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                modifier = Modifier
                    .size(40.dp)
                    .clickable { navController.navigate(Screen.HomeScreen.route) },
                tint = offWhite,
                imageVector = Icons.Default.Home,
                contentDescription = null
            )
        }

        //Box for Create Route Screen
        Box(
            modifier = Modifier.fillMaxHeight(),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                modifier = Modifier
                    .size(40.dp)
                    .clickable { navController.navigate(Screen.CreateRouteScreen.route) },
                tint = offWhite,
                imageVector = Icons.Default.Add,
                contentDescription = null
            )
        }

        //Box for Settings Screen
        Box(
            modifier = Modifier.fillMaxHeight(),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                modifier = Modifier
                    .size(40.dp)
                    .clickable { navController.navigate(Screen.SettingsScreen.route) },
                tint = offWhite,
                imageVector = Icons.Default.Settings,
                contentDescription = null
            )
        }
    }
}


