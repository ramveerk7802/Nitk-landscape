package com.example.majorproject.views

import androidx.compose.runtime.Composable
import androidx.navigation.compose.rememberNavController
import com.example.majorproject.NavigationGraph

@Composable
fun StartAppScreeen(){
    val navController = rememberNavController()
    NavigationGraph(navController)
}