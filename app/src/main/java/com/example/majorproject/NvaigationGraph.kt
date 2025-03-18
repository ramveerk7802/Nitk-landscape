package com.example.majorproject

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.majorproject.Utilities.Destination
import com.example.majorproject.views.MainScreen


@Composable
fun NavigationGraph(navController: NavHostController){

    NavHost(
        navController = navController,
        startDestination = Destination.Main
    ){
        composable<Destination.Main> {
            MainScreen()
        }
    }

}