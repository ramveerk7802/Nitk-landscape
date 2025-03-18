package com.example.majorproject.views

import android.content.Context
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material3.Button
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import coil3.compose.AsyncImage
import com.example.majorproject.R
import com.example.majorproject.viewModels.TFLiteViewModel
import com.example.majorproject.viewModelsFactories.TFLiteViewModelFactory


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen() {
    val context = LocalContext.current

    val myViewModel :TFLiteViewModel = viewModel(factory = TFLiteViewModelFactory(context))
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("Nitk Landscape ") }
            )
        }
    ) {
        App(Modifier.padding(it), myViewModel,context)
    }
}

@Composable
private fun App(modifier: Modifier,myViewModel:TFLiteViewModel,context: Context) {
    val predictResult = myViewModel.predictedResult.observeAsState().value
    val isLoadModel = myViewModel.isLoadModel.observeAsState(false).value
    val isProcess = myViewModel.isProcess.observeAsState(false).value
    var imageUri by remember { mutableStateOf("") }

    val galleryLauncher =
        rememberLauncherForActivityResult(contract = ActivityResultContracts.PickVisualMedia()) {uri->
            uri?.let {
                imageUri=it.toString()
            }

        }
    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        if(isLoadModel){
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ){
                Column (
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ){
                    Text(
                        text =  "Loading Nitk LandScape model..."
                    )
                    CircularProgressIndicator()
                }
            }
        }


        AsyncImage(
            model = imageUri.ifEmpty { R.drawable.upload_icon },
            contentDescription = "Test Image",
            modifier = Modifier.size(
                width = 300.dp,
                height = 300.dp
            ).clickable {
                galleryLauncher.launch(PickVisualMediaRequest(
                    ActivityResultContracts.PickVisualMedia.ImageOnly
                ))
            }

        )

        if(!isProcess){
            Button(
                onClick = {

                    myViewModel.classifyImage(imagePath = imageUri)
//                    {
//                        Toast.makeText(context,it,Toast.LENGTH_SHORT).show()
//
//                    }
                }
            ){
                Text(
                    text = "Predict"
                )
            }
        }
        else{
            CircularProgressIndicator()
        }

        predictResult?.let {
            Text(text =
            buildString {
                append(it.first)
                append(" : ")
                append(it.second)

            })
        }
    }

}
