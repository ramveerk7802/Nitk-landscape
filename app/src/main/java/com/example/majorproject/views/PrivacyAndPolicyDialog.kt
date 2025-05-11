package com.example.majorproject.views

import android.app.Dialog
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog


@Composable
fun PrivacyAndPolicyDialog(showDialog: MutableState<Boolean>){
    Dialog(onDismissRequest = { showDialog.value = false}
    ){ Surface(
            shape = RoundedCornerShape(16.dp),
            color = MaterialTheme.colorScheme.background,
            tonalElevation = 8.dp,
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(0.9f)
        ) {
            Column (
                modifier = Modifier.padding(16.dp)
                    .verticalScroll(rememberScrollState()),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ){
                Text(
                    text = "Privacy Policy",
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(bottom = 8.dp))
                Text(
                    text = "This is the privacy policy of Campus Eye. We collect no personal data. This app respects your privacy and only stores data locally unless otherwise noted. Further enhancements will comply with standard data protection practices.",
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.padding(bottom = 16.dp))
                TextButton(
                    onClick = { showDialog.value = false }
                ){
                    Text(text = "Close")
                }
            }

        }
    }
}