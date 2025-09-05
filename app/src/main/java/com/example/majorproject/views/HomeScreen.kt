package com.example.majorproject.views

import android.content.Context
import android.content.Intent
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.NavigationDrawerItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import coil3.compose.AsyncImage
import com.example.majorproject.R
import com.example.majorproject.Utilities.Destination
import com.example.majorproject.Utilities.DrawerNavigationItem
import com.example.majorproject.ui.theme.GRADIENT_COLOR_1
import com.example.majorproject.ui.theme.GRADIENT_COLOR_2
import com.example.majorproject.viewModels.TFLiteViewModel
import com.example.majorproject.viewModelsFactories.TFLiteViewModelFactory
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(navHostController: NavHostController) {
    val context = LocalContext.current
    val myViewModel: TFLiteViewModel = viewModel(factory = TFLiteViewModelFactory(context))
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    var selectedNavigation by remember { mutableStateOf(0) }
    val showPrivacyDialog = remember { mutableStateOf(false) }
    val showNitkVisitBottomSheet = remember { mutableStateOf(false) }
    ModalNavigationDrawer(
        drawerContent = {
            ModalDrawerSheet(
                modifier = Modifier.fillMaxWidth(0.8f)
            ) {
                MyHeader()
                Spacer(modifier = Modifier.height(16.dp))
                DrawerNavigationItem.list.forEachIndexed { index, item ->
                    NavigationDrawerItem(
                        label = {
                            Text(text = item.title)
                        },
                        onClick = {
                            selectedNavigation = index
                            scope.launch {
                                drawerState.close()
                            }
                            when (index) {
                                0 -> {
                                    if (selectedNavigation != 0) {
                                        navHostController.navigate(Destination.Home) {
                                            popUpTo<Destination.Home> {
                                                inclusive = true
                                            }
                                        }
                                    }
                                }

                                1 -> {
                                    showNitkVisitBottomSheet.value = true
                                }

                                2 -> {
                                    shareApp(context)
                                }

                                3 -> {
                                    showPrivacyDialog.value = true
                                }

                                else -> {
                                    scope.launch {
                                        drawerState.close()
                                    }
                                }
                            }
                        },
                        icon = {
                            Icon(
                                painter = painterResource(id = item.icon),
                                contentDescription = item.title
                            )
                        },
                        selected = selectedNavigation == index,
                        modifier = Modifier.padding(NavigationDrawerItemDefaults.ItemPadding)
                    )
                }

            }
        },
        drawerState = drawerState
    ) {
        Scaffold(
            topBar = {
                TopAppBar(
                    modifier = Modifier.background(
                        brush = Brush.verticalGradient(
                            colors = listOf(GRADIENT_COLOR_1, GRADIENT_COLOR_2)
                        )
                    ),
                    title = {
                        Text(text = "Campus Eye")
                    },
                    navigationIcon = {
                        IconButton(
                            onClick = {
                                scope.launch {
                                    drawerState.open()
                                }
                            }
                        ) {
                            Icon(
                                imageVector = Icons.Default.Menu,
                                contentDescription = ""
                            )
                        }
                    },
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = Color.Transparent,
                        titleContentColor = Color.White,
                        navigationIconContentColor = Color.White
                    )
                )
            }
        ) {
            MainContent(Modifier.padding(it), myViewModel, context)
        }
        if (showPrivacyDialog.value) {
            PrivacyAndPolicyDialog(showDialog = showPrivacyDialog)
        }
        if (showNitkVisitBottomSheet.value) {
            NitkBottomSheet(showBottomSheet = showNitkVisitBottomSheet, context = context)
        }
    }
}

fun shareApp(context: Context) {
    val intent = Intent().apply {
        action = Intent.ACTION_SEND
        putExtra(
            Intent.EXTRA_TEXT,
            "https://drive.google.com/file/d/1Vqys57_HMkRbvm-iOlljo_8aB31Kdl8w/view?usp=sharing"
        )
        type = "text/Plain"
    }
    context.startActivity(Intent.createChooser(intent, "Share Campus Eye via"))

}

@Composable
fun MyHeader() {
    Box(
        modifier = Modifier
            .height(140.dp)
            .fillMaxWidth()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(GRADIENT_COLOR_1, GRADIENT_COLOR_2) // Purple to Blue Gradient
                )
            )
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            AsyncImage(
                model = R.drawable.nitk_logo,
                contentDescription = "",
                colorFilter = ColorFilter.tint(color = Color.White),
                modifier = Modifier.size(80.dp)

            )
            Text(
                text = "Campus Eye",
                color = Color.White,
                style = MaterialTheme.typography.titleLarge
            )
        }
    }
}

@Composable
fun MainContent(modifier: Modifier, viewModel: TFLiteViewModel, context: Context) {

    App(
        modifier = modifier,
        myViewModel = viewModel,
        context = context
    )

}


@Composable
fun App(modifier: Modifier, myViewModel: TFLiteViewModel, context: Context) {
    val predictResult = myViewModel.predictedResult.observeAsState().value
    val historyLiveData = myViewModel.historyLiveData.observeAsState().value
    val isLoadModel = myViewModel.isLoadModel.observeAsState(false).value
    val isProcess = myViewModel.isProcess.observeAsState(false).value
    var imageUri by remember { mutableStateOf("") }
    var imageSelect by remember { mutableStateOf(false) }

    val galleryLauncher =
        rememberLauncherForActivityResult(
            contract = ActivityResultContracts.PickVisualMedia()) { uri ->
            uri?.let {
                imageUri = it.toString()
            }
        }
    Column(
        modifier = modifier.padding(16.dp)
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(12.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        if (isLoadModel) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "Loading Nitk LandScape model..."
                    )
                    CircularProgressIndicator()
                }
            }
        }



        AsyncImage(
            model = imageUri.ifEmpty { R.drawable.upload_icon },
            contentDescription = "Test Image",
            modifier = Modifier.size(
                width = 280.dp,
                height = 280.dp
            ).border(
                border = BorderStroke(width = 2.dp,
                    color = Color.DarkGray),
                shape = RoundedCornerShape(10.dp)
            ).clickable {

                galleryLauncher.launch(
                    PickVisualMediaRequest(
                        ActivityResultContracts.PickVisualMedia.ImageOnly
                    )
                )
            }

        )

        if (!isProcess) {
            Button(
                onClick = {
                    if (imageUri.isBlank()) { imageSelect = true } else {
                        imageSelect = false
                        myViewModel.classifyImage(imagePath = imageUri)
                    }
                }
            ) { Text(text = "Predict") }
        } else { CircularProgressIndicator() }

        predictResult?.let {
            ResultView(label = it.first, confidence = it.second)
        }
        if (imageSelect)
            Text(text = "Select the Image")

        historyLiveData?.let {

            val history = it.reversed()
            if(history.size>1){
                Text(text = "History",
                    modifier = Modifier.align(Alignment.Start),
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.ExtraBold,
                    textAlign = TextAlign.Start
                )
                LazyColumn (
                    modifier = Modifier.fillMaxWidth().height(260.dp),
                    verticalArrangement = Arrangement.spacedBy(10.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ){
                    itemsIndexed(history){index,item->
                        if(index!=0){
                            ResultView(label = item.first, confidence = item.second)
                        }
                    }

                }
            }
        }

    }
}


@Composable
fun ResultView(label: String, confidence: Float) {

    Surface(
        modifier = Modifier.fillMaxWidth().padding(horizontal = 10.dp),
        shape = RoundedCornerShape(16.dp),
        border = BorderStroke(
            width = 2.dp,
            color = GRADIENT_COLOR_2
        ),
        tonalElevation = 2.dp,
        shadowElevation = 2.dp
    ) {
        Column(
            modifier = Modifier.fillMaxWidth().padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceAround
            ) {
                Text(
                    text = "Label name",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "Confidence",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceAround
            ) {
                Text(
                    modifier = Modifier.fillMaxWidth(0.7f),
                    text = label,
                    style = MaterialTheme.typography.bodyMedium,

                    )
                Text(
                    text = "$confidence",
                    style = MaterialTheme.typography.bodyMedium,

                    )
            }
        }
    }
}