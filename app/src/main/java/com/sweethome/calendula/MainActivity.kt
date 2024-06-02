package com.sweethome.calendula

import android.os.Bundle
import androidx.compose.runtime.*
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults.topAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.sweethome.calendula.models.AppState
import com.sweethome.calendula.ui.theme.CalendulaTheme
import com.sweethome.calendula.views.ShowPeriodScope
import com.sweethome.calendula.views.ShowTopAppBar

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            CalendulaTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    MainScreen()
                }
            }
        }
    }


}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen() {

//    val context = LocalContext.current
    val appState = AppState.getState()
    var currentPeriod by remember {mutableStateOf(appState.curentPeriodPresentation)}
    var currentLayout by remember { mutableStateOf(appState.currentLayout) }
    var showScope by remember { mutableStateOf(appState.showScope) }

    Scaffold(
        containerColor = MaterialTheme.colorScheme.background,
        contentColor = MaterialTheme.colorScheme.onBackground,
        topBar = {
            TopAppBar(
                colors = topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary,
                    navigationIconContentColor = MaterialTheme.colorScheme.onPrimary
                ),
                title = {
                    Row(modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        ShowTopAppBar(appState, currentPeriod, currentLayout, showScope
                        ) {
//                            Log.d("debugmes","refresh")
                            currentPeriod = appState.curentPeriodPresentation
                            showScope = appState.showScope
                        }
                    }
                },
                navigationIcon = {
                    IconButton({ }) { Icon(Icons.Filled.Menu, contentDescription = "Меню")}
                },
            )
        },
        bottomBar = {},
        floatingActionButton = {
            FloatingActionButton(
                onClick = { },
                containerColor = MaterialTheme.colorScheme.tertiaryContainer,
                contentColor = MaterialTheme.colorScheme.onTertiaryContainer) {
                Icon(Icons.Default.Add, contentDescription = "Add")
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding),
            verticalArrangement = Arrangement.spacedBy(16.dp),
        ) {
//            Log.d("debugmes","20 invoke ShowPeriodScope")
            ShowPeriodScope(appState, showScope, currentPeriod, currentLayout) {
//                Log.d("debugmes","refresh ShowPeriodScope")
                currentPeriod = appState.curentPeriodPresentation
            }
        }
    }
}



@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    CalendulaTheme {
        MainScreen()
    }
}