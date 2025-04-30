package com.example.majorproject

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.majorproject.Utilities.Destination
import com.example.majorproject.views.HomeScreen
import com.example.majorproject.views.SplashScreen


@Composable
fun NavigationGraph(navController: NavHostController){

    NavHost(
        navController = navController,
        startDestination = Destination.Splash
    ){
        composable<Destination.Splash> {
            SplashScreen(navHostController = navController)
        }
        composable<Destination.Home> {
            HomeScreen(navHostController = navController)
        }

    }

}