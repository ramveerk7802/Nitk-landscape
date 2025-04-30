package com.example.majorproject.views

import android.content.Context
import android.content.Intent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.core.net.toUri


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NitkBottomSheet(showBottomSheet:MutableState<Boolean>, context: Context){

    val sheetState = rememberModalBottomSheetState()


    ModalBottomSheet(
        onDismissRequest = {
            showBottomSheet.value = false
        },
        sheetState = sheetState,

    ){
        Column (
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ){
            Text(text = "You're about to visit the NITK website.")
            Spacer(modifier = Modifier.height(16.dp))
            OutlinedButton(onClick = {
                val nitkUrl = "https://www.nitk.ac.in"
                val intent = Intent(Intent.ACTION_VIEW).apply {
                    data = nitkUrl.toUri()
                }
                context.startActivity(intent)
                showBottomSheet.value = false
            }) {
                Text(text = "Proceed")
            }
            Spacer(modifier = Modifier.height(16.dp))
            OutlinedButton(
                onClick = {
                    showBottomSheet.value= false
                }
            ){
                Text(
                    text = "Cancel"
                )
            }


        }

    }


}