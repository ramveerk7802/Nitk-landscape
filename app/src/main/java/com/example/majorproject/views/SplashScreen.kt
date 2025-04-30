package com.example.majorproject.views

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import coil3.compose.AsyncImage
import com.example.majorproject.R
import com.example.majorproject.Utilities.Destination
import com.example.majorproject.ui.theme.MY_BLUE_COLOR
import kotlinx.coroutines.delay


@Composable
fun SplashScreen(navHostController: NavHostController){

    LaunchedEffect(key1 = Unit){
        delay(3000)
        navHostController.navigate(Destination.Home){
            popUpTo<Destination.Splash>{
                inclusive= true
            }
        }
    }



    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ){
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
                AsyncImage(
                    model = R.drawable.campus_logo,
                    contentDescription = "logo",
                    modifier = Modifier.size(150.dp)
                )

            Text(
                text = "Campus Eye",
                style = MaterialTheme.typography.headlineMedium,
                color = MY_BLUE_COLOR
            )
        }
    }
}